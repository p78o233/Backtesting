package com.example.demo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.callback.R;
import com.example.demo.mapper.FilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TokenFilter implements Filter {
    @Autowired
    private FilterMapper filterMapper;
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
                Arrays.asList("/stock/login","/stock/userRegister","/test/getAll","/stock/timmer/insertStock","/stock/timmer/getDailyRecord","/stock/timmer/defaultItem","/stock/timmer/dayWeek","/stock/timmer/getNowState")
            )
    );
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    @ResponseBody
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        System.out.println("===========================请求路径：========================="+path);
        if(!path.split("/")[1].equals("stock")){
            filterChain.doFilter(request, response);
        }
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            如果是options请求则不进行那些校验，这一步一定需要，不然就请求一直不通过
            filterChain.doFilter(request, response);
        }else {
            if (allowedPath) {
//            这里是不需要处理的url进入的方法
                filterChain.doFilter(request, response);
            } else {
//            这里是需要处理的url进入的方法
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
//            检查token是否正确
                String token = request.getHeader("token");
                if (token == null || "".equals(token)) {
                    try {
                        writer = response.getWriter();
                        R r = new R(false, 501, null, "token不能为空");
                        JSONObject callBackResut = JSONObject.parseObject(JSON.toJSONString(r));
                        writer.print(callBackResut);
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    } finally {
                        if (writer != null)
                            writer.close();
                    }
                } else {
                    int result = filterMapper.checkTokenExist(token);
                    if (result > 0) {
                        filterChain.doFilter(request, response);
                    } else {
                        try {
                            writer = response.getWriter();
                            R r = new R(false, 502, null, "token不正确");
                            JSONObject callBackResut = JSONObject.parseObject(JSON.toJSONString(r));
                            writer.print(callBackResut);

                        } catch (IOException e) {
                            System.out.println(e.toString());
                        } finally {
                            if (writer != null)
                                writer.close();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
