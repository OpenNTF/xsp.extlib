/**
 * This is the client side API for the OpenNTF Community Control Library
 *
 * Put any client side javascript which should be available for the entire
 * plugin here.
 *
 * This will create a global javascript variable called OpenNTFXsp. To use:
 *      OpenNTFXsp.Events.hijackPartialRefresh();
 */
var OpenNTFXsp = function() {

    var openNtfXsp = {
        Events: {
            hijackPartialRefresh: function (){
                // Hijack the partial refresh
                XSP._inheritedPartialRefresh = XSP._partialRefresh;
                XSP._partialRefresh = function( method, form, refreshId, options ){
                    // Publish init
                    dojo.publish( 'partialrefresh-init', [ method, form, refreshId, options ]);
                    this._inheritedPartialRefresh( method, form, refreshId, options );
                };

                // Publish start, complete and error states
                dojo.subscribe( 'partialrefresh-init', function( method, form, refreshId, options ){
                    if( options ){
                        // Get any existing callback code
                        var inheritedOnStart = options.onStart;
                        var inheritedOnComplete = options.onComplete;
                        var inheritedOnError = options.onError;

                        options.onStart = function(){
                            dojo.publish( 'partialrefresh-start', [method, form, refreshId, options]);

                            if (inheritedOnStart) {
                                //run the callback code
                                if (typeof inheritedOnStart === "string") {
                                    eval(inheritedOnStart);
                                }else if (typeof inheritedOnStart === "function") {
                                    inheritedOnStart();
                                }
                            }
                        };
                        options.onComplete = function(){
                            dojo.publish( 'partialrefresh-complete', [method, form, refreshId, options]);
                            if (inheritedOnComplete) {
                                //run the callback code
                                if (typeof inheritedOnComplete === "string") {
                                    eval(inheritedOnComplete);
                                }else if (typeof inheritedOnComplete === "function") {
                                    inheritedOnComplete();
                                }
                            }
                        };
                        options.onError = function(){
                            dojo.publish( 'partialrefresh-error', [ method, form, refreshId, options]);
                            if (inheritedOnError) {
                                //run the callback code
                                if (typeof inheritedOnError === "string") {
                                    eval(inheritedOnError);
                                }else if (typeof inheritedOnError === "function") {
                                    inheritedOnError();
                                }
                            }
                        };
                    }
                });
            }
        },
        StringUtil: {
            endsWith: function(fullStr, endsWithStr) {
                if (fullStr.indexOf(endsWithStr) == -1) {
                    return false;
                }else {
                    var endsWithLength = endsWithStr.length;
                    var tempStr = fullStr.substr(fullStr.lastIndexOf(endsWithStr));
                    if (tempStr == endsWithStr) {
                        return true;
                    }else {
                        return false;
                    }
                }
            },
            beginsWith: function(fullStr, beginsWithStr) {
                if (fullStr.indexOf(beginsWithStr) == -1) {
                    return false;
                }else {
                    var beginsWithLength = beginsWithStr.length;
                    var tempStr = fullStr.substr(0,beginsWithLength);
                    if (tempStr == beginsWithStr) {
                        return true;
                    }else {
                        return false;
                    }
                }
            },
            removeUnderScore: function(fullStr) {
                if (fullStr.indexOf("_") > -1) {
                    fullStr = fullStr.replace(/_/g, '');
                }
                return fullStr;
            },
            replaceUnderScore: function(fullStr, replaceStr) {
                if (fullStr.indexOf("_") > -1) {
                    fullStr = fullStr.replace(/_/g, replaceStr);
                }
                return fullStr;
            }
        },
        URL: {
            /**
             * Get the value of a parameter from the url parameter
             * @name String - The parameter name you want the value of
             * @url String - The url String to search for the parameter.
             *      If null, will use the current url
             * @return String
             */
            getUrlParameterByName: function(name, url) {
                if (url == null) {
                    url = location(window.location.search);
                }
                name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
                var regexS = "[\\?&]" + name + "=([^&#]*)";
                var regex = new RegExp(regexS);
                var results = regex.exec(url);
                if(results == null) {
                    return "";
                } else {
                    return decodeURIComponent(results[1].replace(/\+/g, " "));
                }
            },
            /**
             * Get the current url up to and including the .xsp
             * (i.e.) http://www.someSite.com/path/to/your.nsf/index.xsp?documentId=abcd
             * will return http://www.somSite.com/path/to/your.nsf/index.xsp
             */
            getCurrentUrlToXsp: function() {
                var baseUrl = location.href;
                var xspUrl = baseUrl.substring(0, baseUrl.indexOf(".xsp") +4);
                return xspUrl;
            },
            getCurrentUrlHash: function() {
                return OpenNTFXsp.URL.getUrlHash(location.href);
            },
            /**
             * Get the url hash from a passed in url
             * @param url - The URL to get the hash from
             */
            getUrlHash: function(url) {
                var returnVal = null;
                var urlParts = url.split("#");
                if (urlParts.length > 1) {
                    var hashPart = urlParts[1];
                    if (hashPart.indexOf("?") > -1 || hashPart.indexOf("&") > -1) {
                        var questionIndex = hashPart.indexOf("?");
                        var andIndex = hashPart.indexOf("&");
                        if (questionIndex > -1 && questionIndex < andIndex) {
                            returnVal = hashPart.substring(0,questionIndex);
                        }else if (questionIndex == -1 && andIndex > -1) {
                            returnVal = hashPart.substring(0,andIndex);
                        }else if (questionIndex > -1 && andIndex == -1) {
                            returnVal = hashPart.substring(0,questionIndex);
                        }else{
                            returnVal = hashPart;
                        }
                    }else{
                        returnVal = hashPart;
                    }
                }
                return returnVal;
            },
            /**
             * Get the current url up to and including the .nsf
             * (i.e.) http://www.someSite.com/path/to/your.nsf/index.xsp?documentId=abcd
             * will return http://www.someSite.com/path/to/your.nsf
             */
            getCurrentUrlToNsf: function() {
                var baseUrl = location.href;
                var nsfUrl = baseUrl.substring(0, baseUrl.indexOf(".nsf") +4);
                return nsfUrl;
            },
            /**
             * Get the current url up to the your.nsf
             * (i.e.) http://www.someSite.com/path/to/your.nsf/index.xsp?documentId=abcd
             * will return http://www.someSite.com/path/to/
             */
            getCurrentUrlToDbPath: function() {
                var fullUrl = location.href;
                var urlToNsf = fullUrl.substring(0,fullUrl.indexOf(".nsf") +4);
                var urlToDbPath = urlToNsf.substring(0, urlToNsf.lastIndexOf("/") +1);
                return urlToDbPath;
            }
        }
    };

    return openNtfXsp;

}();
