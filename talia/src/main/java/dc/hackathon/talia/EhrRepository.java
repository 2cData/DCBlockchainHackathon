package dc.hackathon.talia;

import static org.apache.hadoop.hbase.TagType.VISIBILITY_TAG_TYPE;

import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.Tag;
import org.apache.hadoop.hbase.TagType;
import org.apache.hadoop.hbase.TagUtil;
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


	  	  public static final String VISIBILITY_LABEL_GENERATOR_CLASS =
	      "hbase.regionserver.scan.visibility.label.generator.class";
	  public static final String SYSTEM_LABEL = "system";
	  public static final Tag SORTED_ORDINAL_SERIALIZATION_FORMAT_TAG = new ArrayBackedTag(
	      TagType.VISIBILITY_EXP_SERIALIZATION_TAG_TYPE,
	      VisibilityConstants.SORTED_ORDINAL_SERIALIZATION_FORMAT_TAG_VAL);
	  private static final String COMMA = ",";

	  private static final ExpressionParser EXP_PARSER = new ExpressionParser();
	  private static final ExpressionExpander EXP_EXPANDER = new ExpressionExpander();
	  
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
	
	public static boolean isVisibilityTagsPresent(Cell cell){
		  if (cell.getTagsLength() == 0) {
		    return false;
		  }
		  Iterator<Tag> tagsIterator=CellUtil.tagsIterator(cell.getTagsArray(),cell.getTagsOffset(),cell.getTagsLength());
		  while (tagsIterator.hasNext()) {
		    Tag tag=tagsIterator.next();
		    if (tag.getType() == VISIBILITY_TAG_TYPE) {
		      return true;
		    }
		  }
		  return false;
		}
	
	  public static Byte extractVisibilityTags(Cell cell, List<Tag> tags) {
		    Byte serializationFormat = null;
		    Iterator<Tag> tagsIterator = CellUtil.tagsIterator(cell);
		    while (tagsIterator.hasNext()) {
		      Tag tag = tagsIterator.next();
		      if (tag.getType() == TagType.VISIBILITY_EXP_SERIALIZATION_TAG_TYPE) {
		        serializationFormat = TagUtil.getValueAsByte(tag);
		      } else if (tag.getType() == VISIBILITY_TAG_TYPE) {
		        tags.add(tag);
		      }
		    }
		    return serializationFormat;
		  }
	  public static Byte extractAndPartitionTags(Cell cell, List<Tag> visTags,
		      List<Tag> nonVisTags) {
		    Byte serializationFormat = null;
		    Iterator<Tag> tagsIterator = CellUtil.tagsIterator(cell);
		    while (tagsIterator.hasNext()) {
		      Tag tag = tagsIterator.next();
			if (tag.getType() == TagType.VISIBILITY_EXP_SERIALIZATION_TAG_TYPE) {
		        serializationFormat = TagUtil.getValueAsByte(tag);
		      } else if (tag.getType() == VISIBILITY_TAG_TYPE) {
		        visTags.add(tag);
		      } else {
		        // ignore string encoded visibility expressions, will be added in replication handling
		        nonVisTags.add(tag);
		      }
		    }
		    return serializationFormat;
		  }
}

