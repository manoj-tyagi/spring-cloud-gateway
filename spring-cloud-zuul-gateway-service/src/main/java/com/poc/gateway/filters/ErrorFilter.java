package com.poc.gateway.filters;
 
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;

@Component
public class ErrorFilter extends ZuulFilter {
 
  @Override
  public String filterType() {
    return "error";
  }
 
  @Override
  public int filterOrder() {
    return 1;
  }
 
  @Override
  public boolean shouldFilter() {
    return true;
  }
 
  @Override
  public Object run() {
   System.out.println("Inside Route Filter");
 
    return null;
  }
}