package dc.hackathon.talia;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

public class EhrUtils implements InitializingBean{
	private String tableName = "ehr";
	private byte[] tableNameAsBytes = Bytes.toBytes("ehr");

	@Resource(name = "hbaseConfiguration")
	private Configuration config;

	@Autowired
	private HbaseTemplate hbaseTemplate;

	@Autowired
	private EhrRepository ehrRepository;

	private HBaseAdmin admin;

	public void initialize() throws IOException {

		if (admin.tableExists(tableNameAsBytes)) {
			if (!admin.isTableDisabled(tableNameAsBytes)) {
				System.out.printf("Disabling %s\n", tableName);
				admin.disableTable(tableNameAsBytes);
			}
			System.out.printf("Deleting %s\n", tableName);
			admin.deleteTable(tableNameAsBytes);
		}

		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		HColumnDescriptor columnDescriptor = new HColumnDescriptor(
				EhrRepository.CF_INFO);
		tableDescriptor.addFamily(columnDescriptor);

		admin.createTable(tableDescriptor);

	}

	public void addUsers() {
		for (int i = 0; i < 10; i++) {
			ehrRepository.save("ehr" + i,"secret" + i);
		}		
	}

	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(config);
	}

}
