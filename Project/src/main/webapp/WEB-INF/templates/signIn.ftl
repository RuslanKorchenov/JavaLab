<#import "header.ftl" as c/>
<#import "spring.ftl" as spring />
<@c.page title="Sign In">

    <div class="main">
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-form">
                        <h2 class="form-title"><@spring.message 'form.sign.in'/></h2>
                        <form method="POST" class="register-form" id="login-form">
                            <#if status??>
                                ${status}
                            </#if>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <div class="form-group">
                                <label for="email"></label>
                                <input type="email" name="email" id="email"
                                       placeholder="<@spring.message 'form.email'/>"/>
                            </div>
                            <div class="form-group">
                                <input type="password" name="password" id="password"
                                       placeholder="<@spring.message 'form.password'/>"/>
                            </div>
                            <div class="form-group">
                                <label>
                                    <input type="checkbox"
                                           name="remember-me"><@spring.message 'form.remember.me'/>
                                </label>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signIn" id="signIn" class="form-submit"
                                       value="<@spring.message 'form.sign.in.button'/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

    </div>
</@c.page>