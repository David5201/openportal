/*     */ package weixin.guanjia.core.util;
/*     */ 
/*     */ import com.baidu.inf.iis.bcs.BaiduBCS;
/*     */ import com.baidu.inf.iis.bcs.auth.BCSCredentials;
/*     */ import com.baidu.inf.iis.bcs.model.BucketSummary;
/*     */ import com.baidu.inf.iis.bcs.model.DownloadObject;
/*     */ import com.baidu.inf.iis.bcs.model.Empty;
/*     */ import com.baidu.inf.iis.bcs.model.ObjectListing;
/*     */ import com.baidu.inf.iis.bcs.model.ObjectMetadata;
/*     */ import com.baidu.inf.iis.bcs.model.ObjectSummary;
/*     */ import com.baidu.inf.iis.bcs.request.GetObjectRequest;
/*     */ import com.baidu.inf.iis.bcs.request.ListBucketRequest;
/*     */ import com.baidu.inf.iis.bcs.request.ListObjectRequest;
/*     */ import com.baidu.inf.iis.bcs.request.PutObjectRequest;
/*     */ import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class BCSUtil
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(BCSUtil.class);
/*  27 */   private static String host = "bcs.duapp.com";
/*     */   private static BCSCredentials credentials;
/*     */   private static BaiduBCS baiduBCS;
/*     */ 
/*     */   public static void putObjectByInputStream(String accessKey, String secretKey, File uploadFile, String bucket, String object)
/*     */     throws FileNotFoundException
/*     */   {
/*  49 */     credentials = new BCSCredentials(accessKey, secretKey);
/*  50 */     baiduBCS = new BaiduBCS(credentials, host);
/*     */ 
/*  52 */     baiduBCS.setDefaultEncoding("UTF-8");
/*  53 */     InputStream fileContent = new FileInputStream(uploadFile);
/*  54 */     ObjectMetadata objectMetadata = new ObjectMetadata();
/*  55 */     objectMetadata.setContentType("text/html");
/*  56 */     objectMetadata.setContentLength(uploadFile.length());
/*  57 */     PutObjectRequest request = new PutObjectRequest(bucket, object, 
/*  58 */       fileContent, objectMetadata);
/*  59 */     ObjectMetadata result = (ObjectMetadata)baiduBCS.putObject(request).getResult();
/*  60 */     log.info(result);
/*     */   }
/*     */ 
/*     */   public static List<BucketSummary> listBucket(String accessKey, String secretKey)
/*     */   {
/*  72 */     credentials = new BCSCredentials(accessKey, secretKey);
/*  73 */     baiduBCS = new BaiduBCS(credentials, host);
/*  74 */     ListBucketRequest listBucketRequest = new ListBucketRequest();
/*  75 */     BaiduBCSResponse response = baiduBCS.listBucket(listBucketRequest);
/*  77 */     List<BucketSummary>  bucketSummaryList = (List<BucketSummary>) response.getResult();
			  for (BucketSummary bucket : bucketSummaryList) {
/*  78 */       log.info(bucket);
/*     */     }
/*  80 */     return bucketSummaryList;
/*     */   }
/*     */ 
/*     */   public static List<ObjectSummary> listObject(String accessKey, String secretKey, String bucket, int start, int limit)
/*     */   {
/*  98 */     credentials = new BCSCredentials(accessKey, secretKey);
/*  99 */     baiduBCS = new BaiduBCS(credentials, host);
/* 100 */     ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
/* 101 */     listObjectRequest.setStart(start);
/* 102 */     listObjectRequest.setLimit(limit);
/*     */ 
/* 114 */     BaiduBCSResponse response = baiduBCS
/* 115 */       .listObject(listObjectRequest);
/* 116 */     return ((ObjectListing)response.getResult()).getObjectSummaries();
/*     */   }
/*     */ 
/*     */   public static List<ObjectSummary> listObject(String accessKey, String secretKey, String bucket)
/*     */   {
/* 132 */     credentials = new BCSCredentials(accessKey, secretKey);
/* 133 */     baiduBCS = new BaiduBCS(credentials, host);
/* 134 */     ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
/* 135 */     BaiduBCSResponse response = baiduBCS
/* 136 */       .listObject(listObjectRequest);
/* 137 */     return ((ObjectListing)response.getResult()).getObjectSummaries();
/*     */   }
/*     */ 
/*     */   public static InputStream getObject(String accessKey, String secretKey, String bucket, String object)
/*     */   {
/* 142 */     credentials = new BCSCredentials(accessKey, secretKey);
/* 143 */     baiduBCS = new BaiduBCS(credentials, host);
/* 144 */     GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
/* 145 */     BaiduBCSResponse baiduBCSResponse = baiduBCS
/* 146 */       .getObject(getObjectRequest);
/* 147 */     InputStream in = ((DownloadObject)baiduBCSResponse.getResult()).getContent();
/* 148 */     return in;
/*     */   }
/*     */ 
/*     */   public static void deleteObject(String accessKey, String secretKey, String bucket, String object)
/*     */   {
/* 161 */     credentials = new BCSCredentials(accessKey, secretKey);
/* 162 */     baiduBCS = new BaiduBCS(credentials, host);
/* 163 */     Empty result = (Empty)baiduBCS.deleteObject(bucket, object).getResult();
/* 164 */     log.info(result);
/*     */   }
/*     */ 
/*     */   public static void putObjectByFile(String accessKey, String secretKey, File uploadFile, String bucket, String object)
/*     */   {
/* 180 */     credentials = new BCSCredentials(accessKey, secretKey);
/* 181 */     baiduBCS = new BaiduBCS(credentials, host);
/* 182 */     PutObjectRequest request = new PutObjectRequest(bucket, object, 
/* 183 */       uploadFile);
/* 184 */     ObjectMetadata metadata = new ObjectMetadata();
/*     */ 
/* 186 */     request.setMetadata(metadata);
/* 187 */     BaiduBCSResponse response = baiduBCS.putObject(request);
/* 188 */     ObjectMetadata objectMetadata = (ObjectMetadata)response.getResult();
/* 189 */     log.info("x-bs-request-id: " + response.getRequestId());
/* 190 */     log.info(objectMetadata);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.BCSUtil
 * JD-Core Version:    0.6.2
 */