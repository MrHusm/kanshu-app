package com.yxsd.kanshu.search.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxsd.kanshu.base.contants.SearchContants;

/**
 * @author qiong.wang
 *
 */
public class IndexManager {
	private static IndexManager indexManager;

	private static String INDEX_DIR;
	private static Analyzer analyzer = new StandardAnalyzer();
	private static Directory directory = null;

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
				INDEX_DIR = "/Users/bangpei/search";
			}

			if (StringUtils.isBlank(INDEX_DIR)) {
				logger.error("没有设置搜索的文件目录，search.folder＝null");
				throw new RuntimeException("没有设置搜索的文件目录，search.folder＝null");
			}

			try {
				directory = FSDirectory.open(new File(INDEX_DIR).toPath());
			} catch (IOException e) {
				logger.error("创建索引文件目录失败", e);
				throw new RuntimeException("创建索引文件目录失败", e);
			}

			// try {
			// IndexWriterConfig config = new IndexWriterConfig(analyzer);
			//
			// indexWriter = new IndexWriter(directory, config);
			// } catch (IOException e) {
			// logger.error("打开索引文件目录失败", e);
			// throw new RuntimeException("打开索引文件目录失败", e);
			// }
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
		try {
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
			Document document = new Document();
			document.add(new TextField(SearchContants.ID, id, Store.YES));
			document.add(new TextField(SearchContants.TABLENAME, tableName, Store.YES));

			for (String key : fieldMap.keySet()) {
				document.add(new TextField(key, fieldMap.get(key), Store.YES));

			}
			indexWriter.addDocument(document);
			indexWriter.commit();
			return true;
		} catch (Exception e) {
			logger.error("创建索引失败", e);
			throw new RuntimeException("创建索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.rollback();
					indexWriter.close();
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
	public List<Map<String, String>> searchIndex(String text, String[] fields) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DirectoryReader ireader = null;

		try {

			ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
			Query query = parser.parse(text);
			ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;

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
		try {

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
			return true;
		} catch (Exception e) {
			logger.error("删除索引失败", e);
			throw new RuntimeException("删除索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.rollback();
					indexWriter.close();
				} catch (IOException e) {
					logger.error("删除索引失败", e);
					throw new RuntimeException("删除索引失败", e);
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
		try {

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
			return true;
		} catch (Exception e) {
			logger.error("更新索引失败", e);
			throw new RuntimeException("更新索引失败", e);
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.rollback();
					indexWriter.close();
				} catch (IOException e) {
					logger.error("更新索引失败", e);
					throw new RuntimeException("更新索引失败", e);
				}
			}
		}
	}

	// public static void main(String[] args) {
	//
	// Thread thread = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// for (int i = 0; i < 100; i++) {
	// Map<String, String> fieldMap = new HashMap<String, String>();
	// fieldMap.put("wqwq" + i, "ynynyn" + i);
	// IndexManager.getManager().createIndex("sdfas" + i, "book", fieldMap);
	//
	// Map<String, String> fieldMap1 = new HashMap<String, String>();
	// fieldMap.put("ynynyn" + i, "wqwq" + i);
	// IndexManager.getManager().createIndex("ddddd" + i, "book", fieldMap1);
	// }
	// }
	// });
	//
	// Thread thread1 = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// for (int i = 0; i < 100; i++) {
	// Map<String, String> fieldMap = new HashMap<String, String>();
	// fieldMap.put("wqwq" + i, "ynynyn" + i);
	// IndexManager.getManager().createIndex("sdfas" + i, "book", fieldMap);
	//
	// Map<String, String> fieldMap1 = new HashMap<String, String>();
	// fieldMap.put("ynynyn" + i, "wqwq" + i);
	// IndexManager.getManager().createIndex("ddddd" + i, "book", fieldMap1);
	// }
	// }
	// });
	//
	// Thread thread2 = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// for (int i = 0; i < 100; i++) {
	// Map<String, String> fieldMap = new HashMap<String, String>();
	// fieldMap.put("wqwq" + i, "ynynyn" + i);
	// long start = System.currentTimeMillis();
	// IndexManager.getManager().createIndex("sdfas" + i, "book", fieldMap);
	// System.out.println(System.currentTimeMillis() - start);
	//
	// }
	// }
	// });
	// thread.start();
	// thread1.start();
	// //
	// thread2.start();
	// List<Map<String, String>> list =
	// IndexManager.getManager().searchIndex("ynynyn1",
	// new String[] { "wqwq", "ynynyn" });
	// // // for (Map<String, String> map : list) {
	// // // System.out.println(map.keySet());
	// // // }
	// //
	// System.out.println(list.size());
	// }

}