package dc.hackathon.talia;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

@Repository
public class EhrRepository {

	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	private String tableName = "ehr";

	public static byte[] CF_INFO = Bytes.toBytes("cfInfo");

	private byte[] qEnrollId = Bytes.toBytes("enrollId");
	private byte[] qEnrollSecret = Bytes.toBytes("enrollSecret");
	private byte[] qMessage = Bytes.toBytes("message");

	public List<Ehr> findAll() {
		return hbaseTemplate.find(tableName, "cfInfo", new RowMapper<Ehr>() {
			//@Override
			public Ehr mapRow(Result result, int rowNum) throws Exception {
				return new Ehr(Bytes.toString(result.getValue(CF_INFO, qEnrollId)), 
							    Bytes.toString(result.getValue(CF_INFO, qEnrollSecret)),
							    Bytes.toString(result.getValue(CF_INFO, qMessage)));
			}
		});

	}

	public Ehr save(final String enrollId, final String enrollSecret, final String message) {
		return hbaseTemplate.execute(tableName, new TableCallback<Ehr>() {
			public Ehr doInTable(HTableInterface table) throws Throwable {
				Ehr ehr = new Ehr(enrollSecret, enrollSecret, message);
				Put p = new Put(Bytes.toBytes(ehr.getEnrollId()));
				p.add(CF_INFO, qEnrollId, Bytes.toBytes(ehr.getEnrollId()));
				p.add(CF_INFO, qEnrollSecret, Bytes.toBytes(ehr.getEnrollSecret()));
				p.add(CF_INFO, qMessage, Bytes.toBytes(ehr.getMessage()));
				table.put(p);
				return ehr;
			}
		});
	}
}
