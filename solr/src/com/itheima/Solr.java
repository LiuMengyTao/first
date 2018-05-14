package com.itheima;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;


public class Solr {

	//增加
	@Test
	public void testAdd() throws Exception {
		SolrServer solrServer=new HttpSolrServer("http://localhost:8080/solr/collection1");
	
		for (int i=1;i<11;i++) {
			//创建文档对象
			SolrInputDocument doc=new SolrInputDocument();
			doc.addField("id", i+"");
			doc.addField("name", "solr标题"+i);
			doc.addField("content", "solr添加内容"+i);
			solrServer.add(doc);
		}
		solrServer.commit();
	}
	
	
	//删除
	@Test
	public void testDelete() throws Exception {
		//创建solr服务 对象
		SolrServer solrServer =new HttpSolrServer("http://localhost:8080/solr/collection1");
		//根据id进行删除，提交事务
//		solrServer.deleteById("1");
		
		//根据查询条件进行删除操作,此条件会被进行分词操作，符合条件的索引会被全部删除。
		solrServer.deleteByQuery("name:solr标题3");
		solrServer.commit();
	}
	
	
	//修改
	@Test
	public void testUpdate() throws Exception {
		//连通solrServer 的服务
		SolrServer solrServer=new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", "1");
		doc.addField("name", "这个我改了");
		doc.addField("content", "这东西原来的内容太难看");
		//自动覆盖id为1 的文档对象索引
		solrServer.add(doc);
		solrServer.commit();
	
	}
	
	//查询
	
	@Test
	public void testQuery() throws Exception {
		//创建solr服务对象
		SolrServer solrServer =new HttpSolrServer("http://localhost:8080/solr/collection1");
		//创建solr查询对象
		SolrQuery query =new SolrQuery();
		//添加查询条件
		query.setQuery("name:solr");
		//获取查询结果
		QueryResponse response = solrServer.query(query);
		SolrDocumentList list=response.getResults();
		
		System.out.println("打印一下总条数："+list.getNumFound());
		
		//遍历结果集
		for(SolrDocument doc:list) {
			System.out.println(doc.get("id"));
			System.out.println(doc.get("name"));
			System.out.println(doc.get("content"));
			System.out.println("=======================");
			
		}
		
	}
}
