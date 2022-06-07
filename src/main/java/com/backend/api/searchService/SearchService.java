package com.backend.api.searchService;

import com.backend.api.auth.AuthController;
import com.backend.api.repoSearchModel.RepoResponse;
import com.backend.api.requestModel.FilterModel;
import com.backend.api.requestModel.Search_After_Model;
import com.backend.api.requestModel.Terms;
import com.backend.api.responseModel.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthController authController;

    private String baseUrl="https://stage.els.scm.mastercard.int:13531";

    public String matchQuery(String typeQuery, String keywordQuery, int sizeQuery, long fromQuery, List<Double>after)
    {
        String query="\"query\"";
        String match="\"match\"";
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String size="\"size\"";
        String cnt=String.valueOf(sizeQuery);
        String from="\"from\"";
        String fromVal=String.valueOf(fromQuery);

//        String req='{'+size+':'+cnt+','+from+':'+fromVal+','+query+':'+'{'+match+':'+'{'+type+':'+keyword+'}'+'}'+'}';

        String req;

        if(after==null||after.size()==0)
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
        }
        else
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
        }



        System.out.println("Request"+req);

        return req;
    }

    public String booleanQuery(int repo,int proj)
    {
        String repoId=String.valueOf(repo);
        String query="\"query\"";
        String bool="\"bool\"";
        String must="\"must\"";
        String match="\"match\"";
        String _id="\"_id\"";
        String id='"'+repoId+'"';

        String req='{'+query+':'+'{'+bool+':'+'{'+must+':'+'{'+match+':'+'{'+_id+':'+id+'}'+'}'+'}'+'}'+'}';

        return req;
    }

    public String filterFunct(String typeQuery, String keywordQuery, List<FilterModel> filterQuery,int sizeQuery,int from,List<Double>after)
    {
        String req="";
        String type1,key1,type2,key2,type3,key3;
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String size="\"size\"";
        String cnt=String.valueOf(sizeQuery);
        String fromVal=String.valueOf(from);

//        String str="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":\"key1\"}},{\"match\":{\"type2\":\"key2\"}},{\"match\":{\"type3\":\"key3\"}}]}}}";

        if(filterQuery.size()==1)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';

//            req="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}}}";

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }


//            req="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":{\"term\":{"+type+":"+keyword+"}},\"filter\":[{\"term\":{"+type1+":"+key1+"}}]}}}";

        }
        else if(filterQuery.size()==2)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';

//            req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}}}";
//            req="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":{\"term\":{"+type+":"+keyword+"}},\"filter\":[{\"term\":{"+type1+":"+key1+"}},{\"term\":{"+type2+":"+key2+"}}]}}}";

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }
        }
        else
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';
            type3='"'+filterQuery.get(2).type+'"';
            key3='"'+filterQuery.get(2).keyword+'"';
//            req="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}}}";

//            req="{\"size\":"+cnt+",\"from\":"+fromVal+",\"query\":{\"bool\":{\"must\":{\"term\":{"+type+":"+keyword+"}},\"filter\":[{\"term\":{"+type1+":"+key1+"}},{\"term\":{"+type2+":"+key2+"}},{\"term\":{"+type3+":"+key3+"}}]}}}";
            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }


        }

        System.out.println("Request"+req);


        return req;
    }

    public ResponseEntity<RepoResponse> searchRepo(int repoId,int projId)
    {
        String req=booleanQuery(repoId,projId);
//        System.out.println("request String"+req);
        String url=baseUrl+"/bitbucket-repository/_search";

        HttpHeaders headers=authController.generateAuth();

        HttpEntity<String> request=new HttpEntity<>(req,headers);

        ResponseEntity<RepoResponse>response=restTemplate.exchange(url, HttpMethod.POST,request,RepoResponse.class);

//        System.out.println(response.getBody());

        return response;

    }
}
