package com.leeson.common.junit;

import javax.persistence.MappedSuperclass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebRoot")
@ContextHierarchy({@org.springframework.test.context.ContextConfiguration(name="parent", locations={"classpath:application-context.xml"}), @org.springframework.test.context.ContextConfiguration(name="child", locations={"classpath:application-mvc.xml"})})
@MappedSuperclass
public abstract class AbstractAllJunitTest
{

  @Autowired
  protected WebApplicationContext wac;
  protected MockMvc mockMvc;

  @Before
  public void setup()
    throws Exception
  {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.junit.AbstractAllJunitTest
 * JD-Core Version:    0.6.2
 */