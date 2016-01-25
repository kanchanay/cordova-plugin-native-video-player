var exec = require("cordova/exec");

module.exports = {
    play: function (path, options, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "NativeVideoPlayer", "play", [path]);
    }
};
