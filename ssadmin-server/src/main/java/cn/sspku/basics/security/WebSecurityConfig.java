package cn.sspku.basics.security;

import cn.sspku.basics.security.jwt.JwtRoleFilter;
import cn.sspku.basics.utils.SecurityUtil;
import cn.sspku.basics.parameter.NoAuthenticationProperties;
import cn.sspku.basics.parameter.sspkuLoginProperties;
import cn.sspku.basics.security.jwt.AuthenticationFailHandler;
import cn.sspku.basics.security.jwt.AuthenticationSuccessHandler;
import cn.sspku.basics.security.jwt.sspkuAccessDeniedHandler;
import cn.sspku.basics.security.permission.MyFilterSecurityInterceptor;
import cn.sspku.basics.security.validate.ImageValidateFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 配置
 * @author 
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private sspkuLoginProperties sspkuLoginProperties;

    @Autowired
    private NoAuthenticationProperties noAuthenticationProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;

    @Autowired
    private sspkuAccessDeniedHandler sspkuAccessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private ImageValidateFilter imageValidateFilter;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @ApiOperation(value = "SpringSecurity配置")
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();
        for(String authenticationUrl : noAuthenticationProperties.getAuthentication()){
            expressionInterceptUrlRegistry.antMatchers(authenticationUrl).permitAll();
        }
        expressionInterceptUrlRegistry.and().formLogin()
                // 默认提示登陆的接口
                .loginPage("/sspku/common/needLogin")
                // 默认登陆的接口
                .loginProcessingUrl("/sspku/login")
                .permitAll()
                // 登陆成功
                .successHandler(authenticationSuccessHandler)
                // 登陆失败
                .failureHandler(authenticationFailHandler)
                .and()
                // IFrame跨域
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                // 请求的身份认证
                .authenticated()
                .and()
                // 跨域配置
                .cors().and()
                // 关闭跨域拦截
                .csrf().disable()
                // 配置JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 系统菜单权限拦截
                .exceptionHandling().accessDeniedHandler(sspkuAccessDeniedHandler)
                .and()
                // 验证码过滤
                .addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
                // 备用过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // JWT认证过滤
                .addFilter(new JwtRoleFilter(authenticationManager(), sspkuLoginProperties, stringRedisTemplate, securityUtil));
    }
}
