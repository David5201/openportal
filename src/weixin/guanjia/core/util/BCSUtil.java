package weixin.guanjia.core.util;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.DownloadObject;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.apache.log4j.Logger;

public class BCSUtil
{
  private static final Logger log = Logger.getLogger(BCSUtil.class);
  private static String host = "bcs.duapp.com";
  private static BCSCredentials credentials;
  private static BaiduBCS baiduBCS;

  public static void putObjectByInputStream(String accessKey, String secretKey, File uploadFile, String bucket, String object)
    throws FileNotFoundException
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);

    baiduBCS.setDefaultEncoding("UTF-8");
    InputStream fileContent = new FileInputStream(uploadFile);
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType("text/html");
    objectMetadata.setContentLength(uploadFile.length());
    PutObjectRequest request = new PutObjectRequest(bucket, object, 
      fileContent, objectMetadata);
    ObjectMetadata result = (ObjectMetadata)baiduBCS.putObject(request).getResult();
    log.info(result);
  }

  public static List<BucketSummary> listBucket(String accessKey, String secretKey)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    ListBucketRequest listBucketRequest = new ListBucketRequest();
    BaiduBCSResponse response = baiduBCS.listBucket(listBucketRequest);
    List<BucketSummary>  bucketSummaryList = (List<BucketSummary>) response.getResult();
			  for (BucketSummary bucket : bucketSummaryList) {
      log.info(bucket);
    }
    return bucketSummaryList;
  }

  public static List<ObjectSummary> listObject(String accessKey, String secretKey, String bucket, int start, int limit)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
    listObjectRequest.setStart(start);
    listObjectRequest.setLimit(limit);

    BaiduBCSResponse response = baiduBCS
      .listObject(listObjectRequest);
    return ((ObjectListing)response.getResult()).getObjectSummaries();
  }

  public static List<ObjectSummary> listObject(String accessKey, String secretKey, String bucket)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
    BaiduBCSResponse response = baiduBCS
      .listObject(listObjectRequest);
    return ((ObjectListing)response.getResult()).getObjectSummaries();
  }

  public static InputStream getObject(String accessKey, String secretKey, String bucket, String object)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
    BaiduBCSResponse baiduBCSResponse = baiduBCS
      .getObject(getObjectRequest);
    InputStream in = ((DownloadObject)baiduBCSResponse.getResult()).getContent();
    return in;
  }

  public static void deleteObject(String accessKey, String secretKey, String bucket, String object)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    Empty result = (Empty)baiduBCS.deleteObject(bucket, object).getResult();
    log.info(result);
  }

  public static void putObjectByFile(String accessKey, String secretKey, File uploadFile, String bucket, String object)
  {
    credentials = new BCSCredentials(accessKey, secretKey);
    baiduBCS = new BaiduBCS(credentials, host);
    PutObjectRequest request = new PutObjectRequest(bucket, object, 
      uploadFile);
    ObjectMetadata metadata = new ObjectMetadata();

    request.setMetadata(metadata);
    BaiduBCSResponse response = baiduBCS.putObject(request);
    ObjectMetadata objectMetadata = (ObjectMetadata)response.getResult();
    log.info("x-bs-request-id: " + response.getRequestId());
    log.info(objectMetadata);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.BCSUtil
 * JD-Core Version:    0.6.2
 */