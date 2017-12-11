package com.yxsd.kanshu.search.manager;

import com.yxsd.kanshu.base.contants.SearchContants;
import com.yxsd.kanshu.base.contants.SearchEnum;
import com.yxsd.kanshu.base.utils.ConfigPropertieUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiong.wang
 *
 */
public class IndexManager {
	private static IndexManager indexManager;

	private static String INDEX_DIR;
	private static Analyzer analyzer = new StandardAnalyzer();
	private static Directory directory = null;

	private static int pageCount = 20;

	private static Directory ramDirectory = null;

	private static final Logger logger = LoggerFactory.getLogger(IndexManager.class);

	/**
	 * 创建索引管理器
	 * 
	 * @return 返回索引管理器对象
	 */
	public static IndexManager getManager() {
		if (indexManager == null) {
			indexManager = new IndexManager();
		} else {
			return indexManager;
		}

		synchronized (indexManager) {

			if (!StringUtils.isBlank(INDEX_DIR)) {
				return indexManager;
			}

			if (StringUtils.isBlank(INDEX_DIR)) {
				 INDEX_DIR = ConfigPropertieUtils.getString("search.folder");
//				INDEX_DIR = "/Users/bangpei/search";
			}

			if (StringUtils.isBlank(INDEX_DIR)) {
				logger.error("没有设置搜索的文件目录，search.folder＝null");
				throw new RuntimeException("没有设置搜索的文件目录，search.folder＝null");
			}

			try {
				directory = FSDirectory.open(new File(INDEX_DIR).toPath());

				// 创建内存索引库
				ramDirectory = new RAMDirectory(FSDirectory.open(new File(INDEX_DIR).toPath()), null);
			} catch (IOException e) {
				logger.error("创建索引文件目录失败", e);
				throw new RuntimeException("创建索引文件目录失败", e);
			}

			return indexManager;
		}

	}

	/**
	 * 创建索引
	 * 
	 * @param id
	 *            对应数据库里面的id
	 * @param tableName
	 *            对应数据库里面的表
	 * @param fieldMap
	 *            需要建索引的字段，key为字段名称（列名称），value为值
	 * @return true表示建索引成功，false表示建索引失败
	 */
	public synchronized boolean createIndex(String id, String tableName, Map<String, String> fieldMap) {
		IndexWriter indexWriter = null;
		IndexWriter ramIndexWriter = null;
		try {

			// 操作内存的IndexWriter
			IndexWriterConfig iwcRam = new IndexWriterConfig(analyzer);
			ramIndexWriter = new IndexWriter(ramDirectory, iwcRam);

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
			
			Document document = new Document();
			document.add(new TextField(SearchContants.ID, id, Store.YES));
			document.add(new TextField(SearchContants.TABLENAME, tableName, Store.YES));

			for (String key : fieldMap.keySet()) {
				TextField field = new TextField(key, fieldMap.get(key), Store.YES);
				// 设置搜索权重
				if (SearchEnum.title.getSearchField().equals(key)) {
					field.setBoost(10f);
				} else if (SearchEnum.author_name.getSearchField().equals(key)) {
					field.setBoost(3f);
				} else if (SearchEnum.author_penname.getSearchField().equals(key)) {
					field.setBoost(3f);
				} else if (SearchEnum.category_sec_name.getSearchField().equals(key)) {
					field.setBoost(2f);
				} else if (SearchEnum.category_thr_name.getSearchField().equals(key)) {
					field.setBoost(3f);
				}
//				else if (SearchEnum.intro.getSearchField().equals(key)) {
//					field.setBoost(1f);
//				}

				document.add(field);
			}
			indexWriter.addDocument(document);
			indexWriter.commit();

			ramIndexWriter.addDocument(document);
			ramIndexWriter.commit();
			return true;
		} catch (Exception e) {
			logger.error("创建索引失败", e);
			throw new RuntimeException("创建索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					logger.error("创建索引失败", e);
					throw new RuntimeException("创建索引失败", e);
				}
			}

			if (ramIndexWriter != null) {
				try {
					ramIndexWriter.close();
				} catch (IOException e) {
					logger.error("创建索引失败", e);
					throw new RuntimeException("创建索引失败", e);
				}
			}
		}
	}

	/**
	 * 查询接口
	 * 
	 * @param text
	 *            查询的内容
	 * @param fields
	 *            在那些列值中进行查询
	 * @return 返回查询的结果，map表示每一个结果其中key为属性名称，value是值
	 */
	public List<Map<String, String>> searchIndex(String text, String[] fields, int pageNo) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DirectoryReader ireader = null;

		try {
			//ireader = DirectoryReader.open(ramDirectory);
			ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
			Query query = parser.parse(text);

			// 查询前多少行数据
			int totle = pageNo * pageCount;
			TopScoreDocCollector topCollector = TopScoreDocCollector.create(totle);
			isearcher.search(query, topCollector);
			// 取数范围
			ScoreDoc[] hits = topCollector.topDocs((pageNo - 1) * pageCount, totle).scoreDocs;

			for (int i = 0; i < hits.length; i++) {
				Map<String, String> map = new HashMap<String, String>();

				Document hitDoc = isearcher.doc(hits[i].doc);
				for (IndexableField indexableField : hitDoc.getFields()) {
					map.put(indexableField.name(), hitDoc.get(indexableField.name()));
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("查询索引失败", e);
			throw new RuntimeException("查询索引失败", e);
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					logger.error("查询索引失败", e);
					throw new RuntimeException("查询索引失败", e);
				}
			}
		}
		return list;
	}

	/**
	 * 删除索引
	 * 
	 * @param fields
	 *            删除条件：一般要放表名称、id
	 * @return true表示删除索引成功，false表示删除索引失败
	 */
	public synchronized boolean deleteIndex(Map<String, String> fields) {
		IndexWriter indexWriter = null;

		IndexWriter ramIndexWriter = null;

		try {

			// 操作内存的IndexWriter
			IndexWriterConfig iwcRam = new IndexWriterConfig(analyzer);
			ramIndexWriter = new IndexWriter(ramDirectory, iwcRam);

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
			Term[] terms = new Term[fields.size()];
			int i = 0;
			for (String key : fields.keySet()) {
				Term term = new Term(key, fields.get(key));
				terms[i] = term;
				i++;
			}
			indexWriter.deleteDocuments(terms);
			indexWriter.commit();

			ramIndexWriter.deleteDocuments(terms);
			ramIndexWriter.commit();
			return true;
		} catch (Exception e) {
			logger.error("删除索引失败", e);
			throw new RuntimeException("删除索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					logger.error("删除索引失败", e);
					throw new RuntimeException("删除索引失败", e);
				}
			}

			if (ramIndexWriter != null) {
				try {
					ramIndexWriter.close();
				} catch (IOException e) {
					logger.error("创建索引失败", e);
					throw new RuntimeException("创建索引失败", e);
				}
			}
		}
	}

	/**
	 * 更新索引
	 * 
	 * @param id
	 *            要更新的id
	 * @param tableName
	 *            对应数据库里面的表
	 * @param fieldMap
	 *            新内容
	 * @return true表示更新索引成功，false表示更新索引失败
	 */
	public synchronized boolean updateIndex(String id, String tableName, Map<String, String> fieldMap) {
		IndexWriter indexWriter = null;

		IndexWriter ramIndexWriter = null;

		try {

			// 操作内存的IndexWriter
			IndexWriterConfig iwcRam = new IndexWriterConfig(analyzer);
			ramIndexWriter = new IndexWriter(ramDirectory, iwcRam);

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);

			Document document = new Document();
			document.add(new TextField(SearchContants.ID, id, Store.YES));
			document.add(new TextField(SearchContants.TABLENAME, tableName, Store.YES));

			for (String key : fieldMap.keySet()) {
				document.add(new TextField(key, fieldMap.get(key), Store.YES));

			}
			indexWriter.updateDocument(new Term(SearchContants.ID, id), document);
			indexWriter.commit();

			ramIndexWriter.updateDocument(new Term(SearchContants.ID, id), document);
			ramIndexWriter.commit();
			return true;
		} catch (Exception e) {
			logger.error("更新索引失败", e);
			throw new RuntimeException("更新索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					logger.error("更新索引失败", e);
					throw new RuntimeException("更新索引失败", e);
				}
			}

			if (ramIndexWriter != null) {
				try {
					ramIndexWriter.close();
				} catch (IOException e) {
					logger.error("创建索引失败", e);
					throw new RuntimeException("创建索引失败", e);
				}
			}
		}
	}

	public static void main(String[] args) {

		// Thread thread = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		for (int i = 0; i < 100; i++) {
			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put(SearchEnum.title.getSearchField(), "升棺发财" + i);
			fieldMap.put(SearchEnum.author_name.getSearchField(), "冯媛 哈智超 李晓");
			fieldMap.put(SearchEnum.intro.getSearchField(),
					"【全网独家】陆垚在上大学时重逢幼儿园同学马俐，虽然彼此心存好感，但由于陆垚有严重的“表白障碍症”，只能眼巴巴看着自己的女神马俐与别人谈恋爱。而自此之后很多年，陆垚只能以朋友的名义爱着马俐，也与她开始了一段“友情不甘、恋人不敢”的长跑。围绕那一片暧昧的感情空间，细腻描写男女主角之间小心翼翼的进退和试探，总有某个场景戳中你过往的某个记忆瞬间。世间有多少表白，总是止于唇齿，葬于岁月。兜兜转转十数年而过，回过头来，发现心底还保留着那份最初的期待，还能找回一段纯粹动人的爱情。");
			fieldMap.put(SearchEnum.author_penname.getSearchField(), "冯媛 哈智超李晓");
			fieldMap.put(SearchEnum.category_sec_name.getSearchField(), "小说");
			fieldMap.put(SearchEnum.category_thr_name.getSearchField(), "影视小说");

			IndexManager.getManager().createIndex("151", "book", fieldMap);

			// Map<String, String> fieldMap1 = new HashMap<String, String>();
			// fieldMap.put("ynynyn" + i, "wqwq" + i);
			// IndexManager.getManager().createIndex("ddddd" + i, "book",
			// fieldMap1);
		}
		// }
		// });
		////
		//// // Thread thread1 = new Thread(new Runnable() {
		//// //
		//// // @Override
		//// // public void run() {
		//// // for (int i = 0; i < 100; i++) {
		//// // Map<String, String> fieldMap = new HashMap<String, String>();
		//// // fieldMap.put("wqwq" + i, "ynynyn" + i);
		//// // IndexManager.getManager().createIndex("sdfas" + i, "book",
		// fieldMap);
		//// //
		//// // Map<String, String> fieldMap1 = new HashMap<String, String>();
		//// // fieldMap.put("ynynyn" + i, "wqwq" + i);
		//// // IndexManager.getManager().createIndex("ddddd" + i, "book",
		//// // fieldMap1);
		//// // }
		//// // }
		//// // });
		//// //
		//// // Thread thread2 = new Thread(new Runnable() {
		//// //
		//// // @Override
		//// // public void run() {
		//// // for (int i = 0; i < 100; i++) {
		//// // Map<String, String> fieldMap = new HashMap<String, String>();
		//// // fieldMap.put("wqwq" + i, "ynynyn" + i);
		//// // long start = System.currentTimeMillis();
		//// // IndexManager.getManager().createIndex("sdfas" + i, "book",
		// fieldMap);
		//// // System.out.println(System.currentTimeMillis() - start);
		//// //
		//// // }
		//// // }
		//// // });
		//// // thread.start();
		//// // thread1.start();
		//// // //
		// thread.start();
		List<Map<String, String>> list = IndexManager.getManager().searchIndex("发财",
				new String[] { SearchEnum.title.getSearchField() }, 1);
		// // for (Map<String, String> map : list) {
		// // System.out.println(map.keySet());
		// // }
		//
		System.out.println(list.size());
	}

}