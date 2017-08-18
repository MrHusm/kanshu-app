package com.kanshu.kanshu.search.manager;

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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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

	private static String INDEX_DIR = "/Users/bangpei/Documents/luceneIndex";
	private static Analyzer analyzer = new StandardAnalyzer();
	private static Directory directory = null;

	/**
	 * 创建索引管理器
	 * 
	 * @return 返回索引管理器对象
	 */
	public static IndexManager getManager() {
		if (indexManager == null) {
			indexManager = new IndexManager();
		}
		try {
			directory = FSDirectory.open(new File(INDEX_DIR).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexManager;
	}

	/**
	 * 创建索引
	 * @param id			对应数据库里面的id
	 * @param fieldMap		需要建索引的字段，key为字段名称（列名称），value为值
	 * @return				true表示建索引成功，false表示建索引失败
	 */
	public boolean createIndex(String id, Map<String, String> fieldMap) {
		IndexWriter indexWriter = null;
		try {
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);
			Document document = new Document();
			document.add(new TextField("id", id, Store.YES));

			for (String key : fieldMap.keySet()) {
				document.add(new TextField(key, fieldMap.get(key), Store.YES));

			}
			indexWriter.addDocument(document);
			indexWriter.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 查询接口
	 * @param text			查询的内容
	 * @param fields		在那些列值中进行查询
	 * @return				返回查询的结果，map表示每一个结果其中key为属性名称，value是值
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
			e.printStackTrace();

		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;

	}

	/**
	 * 删除索引
	 * @param fields   		删除条件：一般要放表名称、id
	 * @return				true表示删除索引成功，false表示删除索引失败
	 */
	public boolean deleteIndex(Map<String, String> fields) {
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
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 更新索引
	 * @param id				要更新的id		
	 * @param fieldMap			新内容
	 * @return					true表示更新索引成功，false表示更新索引失败
	 */
	public boolean updateIndex(String id, Map<String, String> fieldMap) {
		IndexWriter indexWriter = null;
		try {
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, config);

			Document document = new Document();
			document.add(new TextField("id", id, Store.YES));

			for (String key : fieldMap.keySet()) {
				document.add(new TextField(key, fieldMap.get(key), Store.YES));

			}
			indexWriter.updateDocument(new Term("id", id), document);
			indexWriter.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		IndexManager indexManager = getManager();
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("file", "111222要删除的文件目录");
		fieldMap.put("@return", "test如果成功，返回true");
		fieldMap.put("id", "111111");

		// indexManager.createIndex("111111", fieldMap);
		 indexManager.deleteIndex(fieldMap);
//		indexManager.updateIndex("111111", fieldMap);
		List<Map<String, String>> list = indexManager.searchIndex("目录", new String[] { "file" });
		for (Map<String, String> map : list) {
			for (String key : map.keySet()) {
				System.out.println(key);
				System.out.println(map.get(key));
			}

		}
	}
}