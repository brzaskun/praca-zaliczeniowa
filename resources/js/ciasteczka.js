
//obiektowa obsluga ciasteczek
(function() {
 
function getCookie(name){
  var str = '; '+ document.cookie +';';
  var index = str.indexOf('; '+ escape(name) +'=');
  if (index != -1) {
    index += name.length+3;
    var value = str.slice(index, str.indexOf(';', index));
    return unescape(value);
  }
};
 
function setCookie(cookie) {
  var cookieStr = escape(cookie.name)+"=";
  if (typeof cookie.value != "undefined") {
    cookieStr += escape(cookie.value);
  }
  ['domain','path'].forEach(function(prop) {
    if (cookie[prop]) {
      cookieStr += "; "+ prop +"="+ escape(cookie[prop]);
    }
  });
  if (!cookie.expires) {
    var expires = new Date();
    //ta matematyka to jeden dzien 
    expires.setTime(expires.getTime()+24*60*60*1000);
  }
  cookieStr += "; expires="+ expires.toGMTString() +";";
  document.cookie = cookieStr;
};
 
function deleteCookie(cookie) {
  var past = new Date();
  past.setTime(0) // 1970-01-01
  cookie.expires = past;
  setCookie(cookie);
};
 
function Cookie(name) {
  if (!name) {
    throw "Cookie must have a name";
  }
  this.name = name;
  this.refresh();
};
 
Cookie.prototype = {
  'name': null,
  'value': undefined,
  'domain': undefined,
  'path': undefined,
  'expires': undefined,
  'refresh': function() {
    this.value = getCookie(this.name);
  },
  'save': function() {
    setCookie(this);
  },
  'delete': function() {
    deleteCookie(this);
  }
};
 
window.Cookie = Cookie;
})();

