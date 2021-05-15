## 单点登录流程
假设 siqeus-sso-1 与 siqeus-sso-2 应用均为 siques 旗下的应用
那么他们是如何完成与uaa的单点登录的交互的呢

那么其实我们需要明确几点
1. 首先两个应用的端口不一致，可以证明是逻辑上完全独立的两个应用
2. 若还未完成认证，那么两个应用访问首页都会向 uaa登录页面跳转
3. 用户在 uaa 登录页面完成登陆后，会返回token的信息，并向浏览器设置与后端 session 相关联的cookie:jsessionId
4. 此时访问 siqeus-sso-2 应用时， 由于未登录，因此我们直接向获取授权码的链接跳转

http://localhost:8984/oauth/authorize?client_id={clinetId}
&redirect_uri=http://localhost:8092/callback.html&response_type=code&scope=READ&state={state};

5. 后端通过同域名下的cookie获取到相应的后端session,可以得到用户的登录信息，通过查询 客户端与前端传过来的数据保持一致时，自动完成登录，
进入授权状态


ps: 这里我们用微博作为假设，用户管理，客户端管理等是微博后台的功能，siqeus-sso-1 与 siqeus-sso-2 应用是微博旗下的应用，那么单点登录
就可以通过拉取微博开放平台进行用户的登录认证，旗下的应用就可以通过返回的token来获取用户信息。

授权码模式其实也是这个原理，我们通过在微博上进行客户端的配置，那么也可以跳转到微博开放平台进行认证登录，返回token后进行信息的查询。

他们之间的区别是，一个是微博配置好的客户端应用，另一个是第三方的应用。