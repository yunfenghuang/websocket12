package com.example.demo;

import com.example.demo.lightcouch.CouchDbClient;
import com.example.demo.model.Foo;
import com.example.demo.model.SocketMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	private static final Logger m_logger = LogManager.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		m_logger.info("|======================================================|");
		m_logger.info("|======================================================|");
		m_logger.info("|---------------(SpringBoot Start Success)-------------|");
		m_logger.info("|======================================================|");
		m_logger.info("|======================================================|");
	}
	/*@Bean
	public BaseDocument getDocument(){
		//todo
		//Server server = new ServerImpl("192.168.1.100",5964);
		Database database = new Database("localhost","dp");
		BaseDocument baseDocument = database.getDocument(BaseDocument.class,"4667488c25f99c2a2fadd900b600325c");

		return baseDocument;
	}
	@Bean
	public Database getDatabase(){
		//todo
		//Server server = new ServerImpl("192.168.1.100",5964);
		Database database = new Database("localhost","dp");
		//BaseDocument baseDocument = database.getDocument(BaseDocument.class,"4667488c25f99c2a2fadd900b60003f9");
		return database;
	}*/

	@Bean
	public CouchDbClient getCouchDbClient(){
		CouchDbClient couchDbClient = new CouchDbClient("dp",true,"http","localhost",5984,"","");
		return couchDbClient;
	}

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private CouchDbClient couchDbClient;



	@GetMapping("/")
	public String index() {
		return "index";
	}
	@MessageMapping("/send")
	@SendTo("/topic/send")
	public SocketMessage send(SocketMessage message) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.date = df.format(new Date());
		return message;
	}

	@Scheduled(fixedRate = 1000)
	@SendTo("/topic/callback")
	public Object callback() throws Exception {
		// 发现消息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map map = new HashMap<String,Object>();

		
		/*ViewResults resultAdHoc = database.adhoc("function(doc) {emit(null, doc.name);}");

		ViewResult<Map> result = database.queryView("_design/view3", Map.class, null, null);


		List<ValueRow<Map>> res =  result.getRows();
		for(ValueRow<Map> valueRow : res){
			System.out.println(valueRow.toString());
		}*/

		/*List<Foo> list = database.view("example/foo")
					.startKey("start-key")
					.endKey("end-key")
					.limit(10)
					.includeDocs(true)
					.query(Foo.class);
*/

		List<Foo> foos = couchDbClient.view("haha").includeDocs(true).query(Foo.class);
		if(foos == null){
			System.out.println("空");
		}else {
			for(Foo foo:foos){
				System.out.println("id:"+foo.getDate());
				System.out.println("date:"+foo.getSSID());
			}
			System.out.println("len:"+foos.size());
		}



		messagingTemplate.convertAndSend("/topic/callback", foos);
		return "callback";
	}






}
