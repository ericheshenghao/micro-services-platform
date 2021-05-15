const FULL_CHARTER = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopgrstuvwxyz';
//应用id
let clientId = 'siques-1';

let clientSecret = '123456';
//授权中心地址
let uaaUri = 'http://localhost:8984/oauth/';

function getAuthorizeUri(state) {
    return uaaUri+'authorize?client_id='+clientId+'&redirect_uri=http://localhost:8092/callback.html&response_type=code&scope=READ&state='+state;
}

function getLogoutUri(accessToken) {
    return uaaUri+'remove/token?redirect_uri=http://localhost:8092/index.html&access_token='+accessToken;
}



function getState() {
    let state='';
    for (let i = 0; i < 6; i++) {
        state += FULL_CHARTER[Math.floor(Math.random() * 52)];
    }
    return state;
}

/**
 * 获取url参数
 */
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return '';
}