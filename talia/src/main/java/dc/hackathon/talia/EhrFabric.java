package dc.hackathon.talia;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.grapebaba.hyperledger.fabric.ErrorResolver;
import me.grapebaba.hyperledger.fabric.Fabric;
import me.grapebaba.hyperledger.fabric.Hyperledger;
import me.grapebaba.hyperledger.fabric.models.ChaincodeID;
import me.grapebaba.hyperledger.fabric.models.ChaincodeInput;
import me.grapebaba.hyperledger.fabric.models.ChaincodeOpPayload;
import me.grapebaba.hyperledger.fabric.models.ChaincodeOpResult;
import me.grapebaba.hyperledger.fabric.models.ChaincodeSpec;
import me.grapebaba.hyperledger.fabric.models.Error;
import me.grapebaba.hyperledger.fabric.models.OK;
import me.grapebaba.hyperledger.fabric.models.OK1;
import me.grapebaba.hyperledger.fabric.models.Secret;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.functions.Action1;

public class EhrFabric {
	private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor();
	private static final String ENROLL_ID = "user_type1_2";
	private static final String ENROLL_SECRET = "54c5466fc1";
	private static final String APP_NAME = "learn-chaincode";
	private static final String APP_PATH = "https://github.com/2cData/learn-chaincode/finished";
	private static final String VP = "https://2f79a5e7cd8c40d6b1541a6dc6814538-vp0.us.blockchain.ibm.com:5003";

	static {
		HTTP_LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
	}

	private static final Fabric FABRIC = Hyperledger.fabric(VP, HTTP_LOGGING_INTERCEPTOR);
	private static final Logger LOG = LoggerFactory.getLogger(Fabric.class);

	public static void main(String[] args) throws Exception {
		FABRIC.createRegistrar(Secret.builder().enrollId(ENROLL_ID).enrollSecret(ENROLL_SECRET).build())
				.subscribe(new Action1<OK>() {
					@Override
					public void call(OK ok) {
						System.out.printf("Create registrar ok message:%s\n", ok);
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						Error error = ErrorResolver.resolve(throwable, Error.class);
						System.out.printf("Error message:%s\n", error);
					}
				});

		FABRIC.getRegistrar(ENROLL_ID).subscribe(new Action1<OK>() {
			@Override
			public void call(OK ok) {
				System.out.printf("Get registrar ok message:%s\n", ok);
			}
		});

		FABRIC.getRegistrarECERT(ENROLL_ID).subscribe(new Action1<OK>() {
			@Override
			public void call(OK ok) {
				System.out.printf("Get registrar ecert ok message:%s\n", ok);
			}
		});

		FABRIC.getRegistrarTCERT(ENROLL_ID).subscribe(new Action1<OK1>() {
			@Override
			public void call(OK1 ok) {
				for (String okString : ok.getOk()) {
					System.out.printf("Get registrar tcert ok message:%s\n", okString);
				}
			}
		});

		FABRIC.chaincode(ChaincodeOpPayload.builder().jsonrpc("2.0").id(1).method("deploy")
				.params(ChaincodeSpec.builder().chaincodeID(ChaincodeID.builder().name(APP_NAME).path(APP_PATH).build())
						.ctorMsg(ChaincodeInput.builder().function("init").args(Arrays.asList("hi there")).build())
						.secureContext(ENROLL_ID).type(ChaincodeSpec.Type.GOLANG).build())
				.build()).subscribe(new Action1<ChaincodeOpResult>() {
					@Override
					public void call(ChaincodeOpResult chaincodeOpResult) {
						System.out.printf("Deploy chaincode result:%s\n", chaincodeOpResult);
					}
				});

		FABRIC.chaincode(ChaincodeOpPayload.builder().jsonrpc("2.0").id(2).method("query")
				.params(ChaincodeSpec.builder().chaincodeID(ChaincodeID.builder().name(APP_NAME).path(APP_PATH).build())
						.ctorMsg(ChaincodeInput.builder().function("read").args(Arrays.asList("hi there")).build())
						.secureContext(ENROLL_ID).type(ChaincodeSpec.Type.GOLANG).build())
				.build()).subscribe(new Action1<ChaincodeOpResult>() {
					@Override
					public void call(ChaincodeOpResult chaincodeOpResult) {
						System.out.printf("Invoke chaincode result:%s\n", chaincodeOpResult);
					}
				});

		FABRIC.chaincode(ChaincodeOpPayload.builder().jsonrpc("2.0").id(3).method("invoke")
				.params(ChaincodeSpec.builder().chaincodeID(ChaincodeID.builder().name(APP_NAME).path(APP_PATH).build())
						.ctorMsg(ChaincodeInput.builder().function("write")
								.args(Arrays.asList("hello world", "goodbye world")).build())
						.secureContext(ENROLL_ID).type(ChaincodeSpec.Type.GOLANG).build())
				.build()).subscribe(new Action1<ChaincodeOpResult>() {
					@Override
					public void call(ChaincodeOpResult chaincodeOpResult) {
						System.out.printf("Invoke chaincode result:%s\n", chaincodeOpResult);
					}
				});
	}
}
