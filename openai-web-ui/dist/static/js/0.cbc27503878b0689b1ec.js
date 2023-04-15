webpackJsonp([0],{"7/ED":function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n("mvHQ"),o=n.n(r),s=n("vLgD"),i=n("zFrg"),a={name:"Chat",data:function(){return{ruleForm:{msg:""},token:"",nikName:"",balanceCount:0,userType:"",rules:{msg:[{required:!0,message:"请输入你的问题或者你要发送的信息",trigger:"blur"}]},loading:!1,answer:"",chatRecords:[],showDefault:!0,queryCount:0,userQuerys:[],submit:!1,eventSource:null}},methods:{cancelRequest:function(){this.submit=!1,this.eventSource.close(),this.myScroll();var e={role:"system",content:this.answer};this.chatRecords.push(e),this.answer=""},codeHighlight:function(e){return hljs.highlightAuto(e).value},submitQuestion:function(){if(""!==this.ruleForm.msg.trim())if(1!=this.submit){this.submit=!0,this.ruleForm.msg="",this.showDefault=!1,this.myScroll();var e=this,t=document.getElementById("userInput").value,n={user:t,system:""};e.userQuerys.push(n);var r={role:"user",content:t};e.chatRecords.push(r);o()(e.chatRecords);s.a.post("/chat/params",e.chatRecords).then(function(t){console.log(t),e.eventSource=new i.EventSourcePolyfill("http://web.niitcxl.cn:8081/chat/context/search/"+t.data,{headers:{token:localStorage.getItem("token")}}),e.eventSource.addEventListener("open",function(t){e.submit=!0;var n,r,o,s,i,a,c,u=new Date;e.userQuerys[e.userQuerys.length-1].time=(r=(n=u).getFullYear(),o=n.getMonth()+1,s=n.getDate(),i=n.getHours(),a=n.getMinutes(),c=n.getSeconds(),r+"-"+o+"-"+s+" "+i+":"+a+":"+c),e.ruleForm.msg=""}),e.eventSource.addEventListener("message",function(t){if(console.log(t.data),"[DONE]"===t.data){e.submit=!1,e.eventSource.close(),e.myScroll();var n={role:"system",content:e.answer};e.chatRecords.push(n),e.answer=""}else{var r=t.data;e.userQuerys[e.userQuerys.length-1].system+=r.replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/\\n+/g,"<br/>"),e.answer+=r}}),e.eventSource.addEventListener("error",function(t){(t&&401===t.status&&console.log("not authorized"),500===t.status)&&(e.userQuerys[e.userQuerys.length-1].system="我卡住了，请重试！",e.submit=!1,e.eventSource.close())})})}else this.$notify.error({title:"错误",message:"请等待Ai回答完成！"});else this.$notify.error({title:"错误",message:"请输入您的问题！"})},resetForm:function(e){this.$refs[e].resetFields()},goAdminViews:function(){this.$router.push("/admin")},flushUserInfo:function(){var e=this;s.a.get("/user").then(function(t){e.nikName=t.nikName,e.balanceCount=t.balanceCount,e.userType=t.userType,503==t.code&&(e.$notify.error({title:"错误",message:"会话过期,请重新登录!"}),e.$router.push("/login"))})},exit:function(){localStorage.removeItem("token"),this.$router.push("/login")},myScroll:function(){var e="div-"+(this.userQuerys.length-1);this.$nextTick(function(){document.getElementById(e).scrollIntoView()})}},created:function(){this.token=localStorage.getItem("token"),this.chatRecords=[],this.userQuerys=[]}},c={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-row",{attrs:{gutter:24}},[n("el-col",{attrs:{xs:0,sm:2,md:5,lg:5,xl:5}},[n("div",{staticClass:"grid-content"})]),e._v(" "),n("el-col",{attrs:{xs:24,sm:20,md:14,lg:14,xl:14}},[n("div",{staticClass:"grid-content"},[this.showDefault?n("div",{staticStyle:{"text-align":"center",height:"80vh"}},[n("p",{staticStyle:{"font-weight":"bold","font-size":"35px"}},[e._v("Open AI ChatGPT")]),e._v(" "),n("p",{staticStyle:{"font-weight":"bold","font-size":"20px"}},[e._v("软件持续开发更新中...")]),e._v(" "),n("span",[e._v("基于 OpenAI 的 ChatGPT 自然语言模型人工智能对话。")]),n("br"),e._v(" "),n("span",[e._v("ChatGPT 可以为您做些什么：")]),n("br"),e._v(" "),n("span",[e._v("⑴. 帮写代码、文案、论文、小说")]),n("br"),e._v(" "),n("span",[e._v("⑵. 帮文案润色、写诗作词、谱曲")]),n("br"),e._v(" "),n("span",[e._v("⑶. 帮扮演面试官、书籍电影角色")]),n("br"),e._v(" "),n("span",[e._v("⑷. 陪聊倾诉、解忧解惑、讲故事")]),n("br"),e._v(" "),n("span",[e._v("⑸. 帮您翻译、绘图、设计、构思")]),n("br"),e._v(" "),n("span",[n("strong",[e._v("注意：以上内容均为原创内容！！")])]),n("br"),e._v(" "),n("span",[e._v("【例1：写一篇800字关于春天的作文，要有花有草，有动物。】")]),n("br"),e._v(" "),n("span",[e._v("【例2：写一首关于爱情的歌，主题是午夜思念曾经深爱的她。】")]),n("br"),e._v(" "),n("span",[e._v("【例3：翻译xxxxx为英文.中文】")]),n("br"),e._v(" "),n("span",[e._v("【例4：用C#语言写一个SMTP邮件群发软件。】")]),n("br")]):e._e(),e._v(" "),this.showDefault?e._e():n("div",{staticClass:"scroll_box"},e._l(this.userQuerys,function(t,r){return n("div",{key:r,staticClass:"list_item",attrs:{id:"div-"+r}},[n("div",{staticClass:"ti"},[e._v("回复时间："+e._s(t.time)+" "),n("el-link",{attrs:{underline:!1,type:"danger"},on:{click:e.cancelRequest}},[e._v("取消回复")])],1),e._v(" "),n("div",{staticClass:"mine"},[n("strong",[e._v("M: ")]),e._v(" "),n("div",[e._v(e._s(t.user))])]),e._v(" "),n("div",{staticClass:"ai",attrs:{id:"div-"+r}},[n("strong",[e._v("A: ")]),e._v(" "),n("div",{staticStyle:{"white-space":"pre-wrap"},domProps:{innerHTML:e._s(t.system)}})])])}),0)]),e._v(" "),n("div",{staticClass:"input"},[n("el-form",{staticClass:"queryForm",attrs:{rules:e.rules}},[n("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入内容",id:"userInput"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.submitQuestion.apply(null,arguments)}},model:{value:e.ruleForm.msg,callback:function(t){e.$set(e.ruleForm,"msg",t)},expression:"ruleForm.msg"}},[n("el-button",{staticClass:"append",attrs:{slot:"append",icon:"el-icon-search",loading:this.submit},on:{click:e.submitQuestion},slot:"append"})],1)],1)],1)]),e._v(" "),n("el-col",{attrs:{xs:0,sm:2,md:5,lg:5,xl:5}},[n("div",{staticClass:"grid-content"})])],1)},staticRenderFns:[]};var u=n("VU/8")(a,c,!1,function(e){n("fIef")},"data-v-cda40c56",null);t.default=u.exports},fIef:function(e,t){},mvHQ:function(e,t,n){e.exports={default:n("qkKv"),__esModule:!0}},qkKv:function(e,t,n){var r=n("FeBl"),o=r.JSON||(r.JSON={stringify:JSON.stringify});e.exports=function(e){return o.stringify.apply(o,arguments)}},zFrg:function(e,t,n){var r,o,s;
/** @license
 * eventsource.js
 * Available under MIT License (MIT)
 * https://github.com/Yaffle/EventSource/
 */
/** @license
 * eventsource.js
 * Available under MIT License (MIT)
 * https://github.com/Yaffle/EventSource/
 */
!function(n){"use strict";var i=n.setTimeout,a=n.clearTimeout,c=n.XMLHttpRequest,u=n.XDomainRequest,l=n.ActiveXObject,d=n.EventSource,h=n.document,p=n.Promise,v=n.fetch,f=n.Response,y=n.TextDecoder,g=n.TextEncoder,m=n.AbortController;if("undefined"==typeof window||void 0===h||"readyState"in h||null!=h.body||(h.readyState="loading",window.addEventListener("load",function(e){h.readyState="complete"},!1)),null==c&&null!=l&&(c=function(){return new l("Microsoft.XMLHTTP")}),void 0==Object.create&&(Object.create=function(e){function t(){}return t.prototype=e,new t}),Date.now||(Date.now=function(){return(new Date).getTime()}),void 0==m){var _=v;v=function(e,t){var n=t.signal;return _(e,{headers:t.headers,credentials:t.credentials,cache:t.cache}).then(function(e){var t=e.body.getReader();return n._reader=t,n._aborted&&n._reader.cancel(),{status:e.status,statusText:e.statusText,headers:e.headers,body:{getReader:function(){return t}}}})},m=function(){this.signal={_reader:null,_aborted:!1},this.abort=function(){null!=this.signal._reader&&this.signal._reader.cancel(),this.signal._aborted=!0}}}function b(){this.bitsNeeded=0,this.codePoint=0}b.prototype.decode=function(e){function t(e,t,n){if(1===n)return e>=128>>t&&e<<t<=2047;if(2===n)return e>=2048>>t&&e<<t<=55295||e>=57344>>t&&e<<t<=65535;if(3===n)return e>=65536>>t&&e<<t<=1114111;throw new Error}function n(e,t){if(6===e)return t>>6>15?3:t>31?2:1;if(12===e)return t>15?3:2;if(18===e)return 3;throw new Error}for(var r="",o=this.bitsNeeded,s=this.codePoint,i=0;i<e.length;i+=1){var a=e[i];0!==o&&(a<128||a>191||!t(s<<6|63&a,o-6,n(o,s)))&&(o=0,s=65533,r+=String.fromCharCode(s)),0===o?(a>=0&&a<=127?(o=0,s=a):a>=192&&a<=223?(o=6,s=31&a):a>=224&&a<=239?(o=12,s=15&a):a>=240&&a<=247?(o=18,s=7&a):(o=0,s=65533),0===o||t(s,o,n(o,s))||(o=0,s=65533)):(o-=6,s=s<<6|63&a),0===o&&(s<=65535?r+=String.fromCharCode(s):(r+=String.fromCharCode(55296+(s-65535-1>>10)),r+=String.fromCharCode(56320+(s-65535-1&1023))))}return this.bitsNeeded=o,this.codePoint=s,r};void 0!=y&&void 0!=g&&function(){try{return"test"===(new y).decode((new g).encode("test"),{stream:!0})}catch(e){console.debug("TextDecoder does not support streaming option. Using polyfill instead: "+e)}return!1}()||(y=b);var w=function(){};function C(e){this.withCredentials=!1,this.readyState=0,this.status=0,this.statusText="",this.responseText="",this.onprogress=w,this.onload=w,this.onerror=w,this.onreadystatechange=w,this._contentType="",this._xhr=e,this._sendTimeout=0,this._abort=w}function S(e){return e.replace(/[A-Z]/g,function(e){return String.fromCharCode(e.charCodeAt(0)+32)})}function E(e){for(var t=Object.create(null),n=e.split("\r\n"),r=0;r<n.length;r+=1){var o=n[r].split(": "),s=o.shift(),i=o.join(": ");t[S(s)]=i}this._map=t}function x(){}function T(e){this._headers=e}function R(){}function A(){this._listeners=Object.create(null)}function O(e){i(function(){throw e},0)}function D(e){this.type=e,this.target=void 0}function I(e,t){D.call(this,e),this.data=t.data,this.lastEventId=t.lastEventId}function k(e,t){D.call(this,e),this.status=t.status,this.statusText=t.statusText,this.headers=t.headers}function N(e,t){D.call(this,e),this.error=t.error}C.prototype.open=function(e,t){this._abort(!0);var n=this,r=this._xhr,o=1,s=0;this._abort=function(e){0!==n._sendTimeout&&(a(n._sendTimeout),n._sendTimeout=0),1!==o&&2!==o&&3!==o||(o=4,r.onload=w,r.onerror=w,r.onabort=w,r.onprogress=w,r.onreadystatechange=w,r.abort(),0!==s&&(a(s),s=0),e||(n.readyState=4,n.onabort(null),n.onreadystatechange())),o=0};var u=function(){if(1===o){var e=0,t="",s=void 0;if("contentType"in r)e=200,t="OK",s=r.contentType;else try{e=r.status,t=r.statusText,s=r.getResponseHeader("Content-Type")}catch(n){e=0,t="",s=void 0}0!==e&&(o=2,n.readyState=2,n.status=e,n.statusText=t,n._contentType=s,n.onreadystatechange())}},l=function(){if(u(),2===o||3===o){o=3;var e="";try{e=r.responseText}catch(e){}n.readyState=3,n.responseText=e,n.onprogress()}},d=function(e,t){if(null!=t&&null!=t.preventDefault||(t={preventDefault:w}),l(),1===o||2===o||3===o){if(o=4,0!==s&&(a(s),s=0),n.readyState=4,"load"===e)n.onload(t);else if("error"===e)n.onerror(t);else{if("abort"!==e)throw new TypeError;n.onabort(t)}n.onreadystatechange()}},h=function(){s=i(function(){h()},500),3===r.readyState&&l()};"onload"in r&&(r.onload=function(e){d("load",e)}),"onerror"in r&&(r.onerror=function(e){d("error",e)}),"onabort"in r&&(r.onabort=function(e){d("abort",e)}),"onprogress"in r&&(r.onprogress=l),"onreadystatechange"in r&&(r.onreadystatechange=function(e){!function(e){void 0!=r&&(4===r.readyState?"onload"in r&&"onerror"in r&&"onabort"in r||d(""===r.responseText?"error":"load",e):3===r.readyState?"onprogress"in r||l():2===r.readyState&&u())}(e)}),!("contentType"in r)&&"ontimeout"in c.prototype||(t+=(-1===t.indexOf("?")?"?":"&")+"padding=true"),r.open(e,t,!0),"readyState"in r&&(s=i(function(){h()},0))},C.prototype.abort=function(){this._abort(!1)},C.prototype.getResponseHeader=function(e){return this._contentType},C.prototype.setRequestHeader=function(e,t){var n=this._xhr;"setRequestHeader"in n&&n.setRequestHeader(e,t)},C.prototype.getAllResponseHeaders=function(){return void 0!=this._xhr.getAllResponseHeaders&&this._xhr.getAllResponseHeaders()||""},C.prototype.send=function(){if("ontimeout"in c.prototype&&("sendAsBinary"in c.prototype||"mozAnon"in c.prototype)||void 0==h||void 0==h.readyState||"complete"===h.readyState){var e=this._xhr;"withCredentials"in e&&(e.withCredentials=this.withCredentials);try{e.send(void 0)}catch(e){throw e}}else{var t=this;t._sendTimeout=i(function(){t._sendTimeout=0,t.send()},4)}},E.prototype.get=function(e){return this._map[S(e)]},null!=c&&null==c.HEADERS_RECEIVED&&(c.HEADERS_RECEIVED=2),x.prototype.open=function(e,t,n,r,o,s,i){e.open("GET",o);var a=0;for(var u in e.onprogress=function(){var t=e.responseText.slice(a);a+=t.length,n(t)},e.onerror=function(e){e.preventDefault(),r(new Error("NetworkError"))},e.onload=function(){r(null)},e.onabort=function(){r(null)},e.onreadystatechange=function(){if(e.readyState===c.HEADERS_RECEIVED){var n=e.status,r=e.statusText,o=e.getResponseHeader("Content-Type"),s=e.getAllResponseHeaders();t(n,r,o,new E(s))}},e.withCredentials=s,i)Object.prototype.hasOwnProperty.call(i,u)&&e.setRequestHeader(u,i[u]);return e.send(),e},T.prototype.get=function(e){return this._headers.get(e)},R.prototype.open=function(e,t,n,r,o,s,i){var a=null,c=new m,u=c.signal,l=new y;return v(o,{headers:i,credentials:s?"include":"same-origin",signal:u,cache:"no-store"}).then(function(e){return a=e.body.getReader(),t(e.status,e.statusText,e.headers.get("Content-Type"),new T(e.headers)),new p(function(e,t){var r=function(){a.read().then(function(t){if(t.done)e(void 0);else{var o=l.decode(t.value,{stream:!0});n(o),r()}}).catch(function(e){t(e)})};r()})}).catch(function(e){return"AbortError"===e.name?void 0:e}).then(function(e){r(e)}),{abort:function(){null!=a&&a.cancel(),c.abort()}}},A.prototype.dispatchEvent=function(e){e.target=this;var t=this._listeners[e.type];if(void 0!=t)for(var n=t.length,r=0;r<n;r+=1){var o=t[r];try{"function"==typeof o.handleEvent?o.handleEvent(e):o.call(this,e)}catch(e){O(e)}}},A.prototype.addEventListener=function(e,t){e=String(e);var n=this._listeners,r=n[e];void 0==r&&(r=[],n[e]=r);for(var o=!1,s=0;s<r.length;s+=1)r[s]===t&&(o=!0);o||r.push(t)},A.prototype.removeEventListener=function(e,t){e=String(e);var n=this._listeners,r=n[e];if(void 0!=r){for(var o=[],s=0;s<r.length;s+=1)r[s]!==t&&o.push(r[s]);0===o.length?delete n[e]:n[e]=o}},I.prototype=Object.create(D.prototype),k.prototype=Object.create(D.prototype),N.prototype=Object.create(D.prototype);var H=-1,P=0,Q=1,j=2,F=-1,M=0,q=1,L=2,$=3,z=/^text\/event\-stream(;.*)?$/i,G=function(e,t){var n=null==e?t:parseInt(e,10);return n!=n&&(n=t),V(n)},V=function(e){return Math.min(Math.max(e,1e3),18e6)},B=function(e,t,n){try{"function"==typeof t&&t.call(e,n)}catch(e){O(e)}};function J(e,t){A.call(this),t=t||{},this.onopen=void 0,this.onmessage=void 0,this.onerror=void 0,this.url=void 0,this.readyState=void 0,this.withCredentials=void 0,this.headers=void 0,this._close=void 0,function(e,t,n){t=String(t);var r=Boolean(n.withCredentials),o=n.lastEventIdQueryParameterName||"lastEventId",s=V(1e3),l=G(n.heartbeatTimeout,45e3),d="",h=s,p=!1,v=0,f=n.headers||{},y=n.Transport,g=U&&void 0==y?void 0:new C(void 0!=y?new y:void 0!=c&&"withCredentials"in c.prototype||void 0==u?new c:new u),m=null!=y&&"string"!=typeof y?new y:void 0==g?new R:new x,_=void 0,b=0,w=H,S="",E="",T="",A="",O=M,D=0,J=0,X=function(t,n,r,o){if(w===P)if(200===t&&void 0!=r&&z.test(r)){w=Q,p=Date.now(),h=s,e.readyState=Q;var i=new k("open",{status:t,statusText:n,headers:o});e.dispatchEvent(i),B(e,e.onopen,i)}else{var a="";200!==t?(n&&(n=n.replace(/\s+/g," ")),a="EventSource's response has a status "+t+" "+n+" that is not 200. Aborting the connection."):a="EventSource's response has a Content-Type specifying an unsupported type: "+(void 0==r?"-":r.replace(/\s+/g," "))+". Aborting the connection.",Z();var i=new k("error",{status:t,statusText:n,headers:o});e.dispatchEvent(i),B(e,e.onerror,i),console.error(a)}},K=function(t){if(w===Q){for(var n=-1,r=0;r<t.length;r+=1){var o=t.charCodeAt(r);o!=="\n".charCodeAt(0)&&o!=="\r".charCodeAt(0)||(n=r)}var c=(-1!==n?A:"")+t.slice(0,n+1);A=(-1===n?A:"")+t.slice(n+1),""!==t&&(p=Date.now(),v+=t.length);for(var u=0;u<c.length;u+=1){var o=c.charCodeAt(u);if(O===F&&o==="\n".charCodeAt(0))O=M;else if(O===F&&(O=M),o==="\r".charCodeAt(0)||o==="\n".charCodeAt(0)){if(O!==M){O===q&&(J=u+1);var f=c.slice(D,J-1),y=c.slice(J+(J<u&&c.charCodeAt(J)===" ".charCodeAt(0)?1:0),u);"data"===f?(S+="\n",S+=y):"id"===f?E=y:"event"===f?T=y:"retry"===f?(s=G(y,s),h=s):"heartbeatTimeout"===f&&(l=G(y,l),0!==b&&(a(b),b=i(function(){W()},l)))}if(O===M){if(""!==S){d=E,""===T&&(T="message");var g=new I(T,{data:S.slice(1),lastEventId:E});if(e.dispatchEvent(g),"open"===T?B(e,e.onopen,g):"message"===T?B(e,e.onmessage,g):"error"===T&&B(e,e.onerror,g),w===j)return}S="",T=""}O=o==="\r".charCodeAt(0)?F:M}else O===M&&(D=u,O=q),O===q?o===":".charCodeAt(0)&&(J=u+1,O=L):O===L&&(O=$)}}},Y=function(t){if(w===Q||w===P){w=H,0!==b&&(a(b),b=0),b=i(function(){W()},h),h=V(Math.min(16*s,2*h)),e.readyState=P;var n=new N("error",{error:t});e.dispatchEvent(n),B(e,e.onerror,n),void 0!=t&&console.error(t)}},Z=function(){w=j,void 0!=_&&(_.abort(),_=void 0),0!==b&&(a(b),b=0),e.readyState=j},W=function(){if(b=0,w===H){p=!1,v=0,b=i(function(){W()},l),w=P,S="",T="",E=d,A="",D=0,J=0,O=M;var n=t;if("data:"!==t.slice(0,5)&&"blob:"!==t.slice(0,5)&&""!==d){var r=t.indexOf("?");n=-1===r?t:t.slice(0,r+1)+t.slice(r+1).replace(/(?:^|&)([^=&]*)(?:=[^&]*)?/g,function(e,t){return t===o?"":e}),n+=(-1===t.indexOf("?")?"?":"&")+o+"="+encodeURIComponent(d)}var s=e.withCredentials,a={Accept:"text/event-stream"},c=e.headers;if(void 0!=c)for(var u in c)Object.prototype.hasOwnProperty.call(c,u)&&(a[u]=c[u]);try{_=m.open(g,X,K,Y,n,s,a)}catch(e){throw Z(),e}}else if(p||void 0==_){var h=Math.max((p||Date.now())+l-Date.now(),1);p=!1,b=i(function(){W()},h)}else Y(new Error("No activity within "+l+" milliseconds. "+(w===P?"No response received.":v+" chars received.")+" Reconnecting.")),void 0!=_&&(_.abort(),_=void 0)};e.url=t,e.readyState=P,e.withCredentials=r,e.headers=f,e._close=Z,W()}(this,e,t)}var U=void 0!=v&&void 0!=f&&"body"in f.prototype;J.prototype=Object.create(A.prototype),J.prototype.CONNECTING=P,J.prototype.OPEN=Q,J.prototype.CLOSED=j,J.prototype.close=function(){this._close()},J.CONNECTING=P,J.OPEN=Q,J.CLOSED=j,J.prototype.withCredentials=void 0;var X=d;void 0==c||void 0!=d&&"withCredentials"in d.prototype||(X=J),function(n){if("object"==typeof e&&"object"==typeof e.exports){var i=n(t);void 0!==i&&(e.exports=i)}else o=[t],void 0===(s="function"==typeof(r=n)?r.apply(t,o):r)||(e.exports=s)}(function(e){e.EventSourcePolyfill=J,e.NativeEventSource=d,e.EventSource=X})}("undefined"==typeof globalThis?"undefined"!=typeof window?window:"undefined"!=typeof self?self:this:globalThis)}});
//# sourceMappingURL=0.cbc27503878b0689b1ec.js.map