package com.project.batacademy.filters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.project.batacademy.domain.UserAccount;
import com.project.batacademy.services.UserService;

/* NOTE: Register the filter like the REST handlers -- in this case it's done in the RestApplicationConfig class  */
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {
	private Logger logger = Logger.getLogger(AuthorizationFilter.class);
	
	private void fail(ContainerRequestContext requestContext, String msg) {
		requestContext.abortWith(
                Response.status(Response.Status.BAD_REQUEST)
                .entity(msg)
                .build());
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//GET, POST, PUT, DELETE, ...
        String method = requestContext.getMethod();
        UriInfo uriInfo = requestContext.getUriInfo();
        String path = uriInfo.getPath();
        String id, pswd;
        UserAccount userAccount;
        
        logger.info("Start Authorization Filter");
        logger.info(path);
        
        if (path.equals("studrest/student/")) {
        	/* Creating a new user, authorization not required */
        	return;
        }
        
        //Get the authorization passed in HTTP headers parameters
        Map<String, List<String>> headers = requestContext.getHeaders();
        List<String> authHeaderList = headers.get("Authorization");
        String auth;
        
        if (authHeaderList == null) {
        	logger.debug("Authorization header is missing");
        	fail(requestContext, "Missing Authorization Header");
        	return;
        }
        
        auth = authHeaderList.get(0);
        String[] userInfoList = BasicAuthorization.decode(auth);
        id = userInfoList[0];
        pswd = userInfoList[1];
        
        logger.debug("Authenticating user: " + id + " with password: " + pswd);
        
//        userAccount = UserService.extractAcctFromAuthorization(auth);
//        if (userAccount == null || !userAccount.hasPassword(pswd)) {
//        	logger.debug("User not found: ");
//        	fail(requestContext, "Invalid Login Credentials");
//        	return;
//        }
        
        logger.debug("User is valid");
        /* Add the userAccount as a property to the request context so it can be looked up up by the rest handlers.
         * Rest handlers should add the following parameter to get access to this property (it will be injected if 
         * the annotation is added:
         *         	@Context ContainerRequestContext request
         * Then it can be looked up using:
         *         UserAccount user = (UserAccount) request.getProperty("user");
         */
 //       requestContext.setProperty("user", userAccount);
	}
}
