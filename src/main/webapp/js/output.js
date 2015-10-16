"use strict";
function checkBrowser() {
    var browser = getBrowserNameAndVersion();
    return "msie" === browser[0].toLowerCase() && browser[1] < 10 ? "Internet Explorer versions below 10 aren't supported." : "firefox" === browser[0].toLowerCase() && browser[1] < 10 ? "Firefox versions below 10 aren't supported." : "chrome" === browser[0].toLowerCase() && browser[1] < 14 ? "Chrome versions below 14 aren't supported." : "safari" == browser[0].toLowerCase() && browser[1] < 4 ? "Safari versions below 4 aren't supported." : "opera" === browser[0].toLowerCase() && browser[1] < 11 ? "Opera versions below 11 aren't supported." : "Supported"
}
function getBrowserNameAndVersion() {
    var tem, ua = navigator.userAgent, M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
    return /trident/i.test(M[1]) ? (tem = /\brv[ :]+(\d+)/g.exec(ua) || [], ["MSIE ", tem[1] || ""]) : "Chrome" === M[1] && (tem = ua.match(/\b(OPR|Edge)\/(\d+)/), null != tem) ? [tem[1].replace("OPR", "Opera"), tem[2]] : (M = M[2] ? [M[1], M[2]] : [navigator.appName, navigator.appVersion], null != (tem = ua.match(/version\/(\d+)/i)) && (M = [M[0], tem[1]]), M)
}
function DocRoot($scope, $location, $localStorage, $filter, $http, $anchorScroll, $timeout) {
    function setView(hash) {
        "warnings" === hash ? $scope.view = "warnings" : (/^-?\d+$/.test(hash) && $scope.openResource($scope.getInterfaceByHash(hash).url, hash), $scope.view = "interfaces")
    }

    function ensureLinksHaveTargetInApiIntro() {
        $("#intro a").attr("target", function (i, current) {
            return current || "_self"
        })
    }

    function appendUrl(rootParts, url, method, hash, rootResource) {
        var currentResource = null, parentResource = rootResource;
        _.each(rootParts, function (rootPart) {
            "" != rootPart ? (currentResource = _.find(parentResource.resources, function (resource) {
                return resource.name === rootPart
            }), currentResource || (currentResource = {
                name: rootPart,
                resources: [],
                leafResources: []
            }, parentResource.resources.push(currentResource)), parentResource = currentResource) : currentResource = parentResource
        });
        var existingLeaf = _.find(currentResource.leafResources, function (leaf) {
            return leaf.url === url
        });
        existingLeaf ? existingLeaf.methods.push({
            method: method,
            hash: hash
        }) : currentResource.leafResources.push({name: "", url: url, methods: [{method: method, hash: hash}]})
    }

    function splitPaths(interfaces) {
        var resources = [{name: "", resources: [], leafResources: []}];
        return _.each(interfaces, function (element) {
            "/" !== element.url.charAt(0) && (element.url = "/" + element.url), "/" !== element.url.charAt(element.url.length - 1) && (element.url += "/");
            var baseUrl = element.url;
            "/" === baseUrl && (baseUrl = ""), appendUrl(baseUrl.split("/"), element.url, element.http, element.hash, resources[0])
        }), _.each(resources, function (subResource) {
            compactEmptyMiddleResources(subResource.resources, subResource.leafResources)
        }), 0 === resources[0].leafResources.length && (resources = resources[0].resources), resources.sort(function (r1, r2) {
            return r1.name.localeCompare(r2.name)
        }), resources
    }

    function compactEmptyMiddleResources(resources, leafResources) {
        var subResourcesToRemove = [];
        _.each(resources, function (subResource, index) {
            var foundLeafResult = findSolitaryLeaf(subResource, "");
            foundLeafResult ? (foundLeafResult.leaf.name = foundLeafResult.aggregatedName, leafResources.push(foundLeafResult.leaf), subResourcesToRemove.push(index)) : compactEmptyMiddleResources(subResource.resources, subResource.leafResources)
        });
        for (var i = subResourcesToRemove.length - 1; i >= 0; i--)resources.splice(subResourcesToRemove[i], 1)
    }

    function findSolitaryLeaf(subResource, name) {
        if (subResource.resources.length + subResource.leafResources.length !== 1)return !1;
        var aggregatedName = concatenateWithSlash(name, subResource.name);
        return 1 === subResource.leafResources.length ? {
            leaf: subResource.leafResources[0],
            aggregatedName: concatenateWithSlash(aggregatedName, subResource.leafResources[0].name)
        } : findSolitaryLeaf(subResource.resources[0], aggregatedName)
    }

    function concatenateWithSlash(firstPart, secondPart) {
        var result = "";
        return firstPart && (result += firstPart), firstPart && secondPart && (result += "/"), secondPart && (result += secondPart), result
    }

    function isResourcePartVisible(resource) {
        return $scope.singlePage ? !1 : resource === $scope.currentResource ? !0 : void 0 !== _.find(resource.resources, function (sub) {
            return isResourcePartVisible(sub)
        })
    }

    function findParent(url) {
        return findParentRecursive($scope.resourceTree, url)
    }

    function findParentRecursive(resources, url) {
        for (var i = 0; i < resources.length; ++i) {
            var resource = resources[i];
            if (0 !== resource.leafResources.length && void 0 !== _.find(resource.leafResources, function (leaf) {
                    return leaf.url === url
                }))return resource;
            if (0 !== resource.resources.length) {
                var result = findParentRecursive(resource.resources, url);
                if (null !== result)return result
            }
        }
        return null
    }

    function setGlobalCollapsedState(collapsed) {
        $localStorage.globalCollapsedState = collapsed, _.each($scope.interfaces, function (currentInterface) {
            currentInterface.collapsed = collapsed
        })
    }

    function collapseResources(resources, open, stopLevel) {
        void 0 === stopLevel || null === stopLevel ? _.each(resources, function (resource) {
            resource.hierarchyOpen = open, collapseResources(resource.resources, open)
        }) : stopLevel < com.qmino.miredot.restApiSource.initialCollapseLevel ? _.each(resources, function (resource) {
            resource.hierarchyOpen = open, collapseResources(resource.resources, open, stopLevel + 1)
        }) : void 0 !== stopLevel && _.each(resources, function (resource) {
            isResourcePartVisible(resource) && (resource.hierarchyOpen = !0, collapseResources(resource.resources, open, stopLevel + 1))
        })
    }

    function countResources(resources, leafResources) {
        var count = 0;
        return leafResources && (count += leafResources.length), resources && (count += resources.length, _.each(resources, function (resource) {
            count += countResources(resource.resources, resource.leafResources)
        })), count
    }

    $scope.restBase = "http://www.miredot.com/miredot/rest/", $scope.visitWebsiteForProVersion = 'Visit our <a href="http://www.miredot.com/price/?licencerequest=pro" target="_blank">website</a> to get the full version (free for open source).', $scope.projectTitle = com.qmino.miredot.restApiSource.projectTitle, $scope.miredotVersion = com.qmino.miredot.restApiSource.miredotVersion, $scope.validLicence = com.qmino.miredot.restApiSource.validLicence, $scope.licenceType = com.qmino.miredot.restApiSource.licenceType, $scope.licenceErrorMessage = com.qmino.miredot.restApiSource.licenceErrorMessage, $scope.licenceHash = com.qmino.miredot.restApiSource.licenceHash, $scope.allowUsageTracking = com.qmino.miredot.restApiSource.allowUsageTracking, $scope.dateOfGeneration = com.qmino.miredot.restApiSource.dateOfGeneration, $scope.issuesTabHidden = com.qmino.miredot.restApiSource.issuesTabHidden, $scope.singlePage = com.qmino.miredot.restApiSource.singlePage, $scope.hideLogoOnTop = com.qmino.miredot.restApiSource.hideLogoOnTop, $scope.$storage = $localStorage.$default({globalCollapsedState: !1}), $scope.baseUrl = {
        value: function () {
            var baseUrl = "http://grndctl.herokuapp.com";
            return $scope.validLicence && "PRO" == $scope.licenceType && (baseUrl = $location.search().baseUrl || com.qmino.miredot.restApiSource.baseUrl || baseUrl), baseUrl
        }()
    }, $scope.editingBaseUrl = !1, $scope.projectWarnings = com.qmino.miredot.projectWarnings, $scope.interfaces = com.qmino.miredot.restApiSource.interfaces, $scope.tos = com.qmino.miredot.restApiSource.tos, $scope.processErrors = com.qmino.miredot.processErrors, $scope.jsonDocConfig = {
        enabled: com.qmino.miredot.restApiSource.jsonDocEnabled,
        hidden: com.qmino.miredot.restApiSource.jsonDocHidden
    }, $scope.searchByExample = "", $scope.searchQuery = {
        url: "",
        http: ""
    }, $scope.location = $location, $scope.navigationView = "hierarchical", $scope.statusCodes = {}, $scope.statusCodes[100] = "Continue", $scope.statusCodes[101] = "Switching Protocols", $scope.statusCodes[200] = "OK", $scope.statusCodes[201] = "Created", $scope.statusCodes[202] = "Accepted", $scope.statusCodes[203] = "Non-Authoritative Information", $scope.statusCodes[204] = "No Content", $scope.statusCodes[205] = "Reset Content", $scope.statusCodes[206] = "Partial Content", $scope.statusCodes[300] = "Multiple Choices", $scope.statusCodes[301] = "Moved Permanently", $scope.statusCodes[302] = "Found", $scope.statusCodes[303] = "See Other", $scope.statusCodes[304] = "Not Modified", $scope.statusCodes[305] = "Use Proxy", $scope.statusCodes[306] = "Switch Proxy", $scope.statusCodes[307] = "Temporary Redirect", $scope.statusCodes[308] = "Permanent Redirect", $scope.statusCodes[400] = "Bad Request", $scope.statusCodes[401] = "Unauthorized", $scope.statusCodes[402] = "Payment Required", $scope.statusCodes[403] = "Forbidden", $scope.statusCodes[404] = "Not Found", $scope.statusCodes[405] = "Method Not Allowed", $scope.statusCodes[406] = "Not Acceptable", $scope.statusCodes[407] = "Proxy Authentication Required", $scope.statusCodes[408] = "Request Timeout", $scope.statusCodes[409] = "Conflict", $scope.statusCodes[410] = "Gone", $scope.statusCodes[411] = "Length Required", $scope.statusCodes[412] = "Precondition Failed", $scope.statusCodes[413] = "Request Entity Too Large", $scope.statusCodes[414] = "Request-URI Too Long", $scope.statusCodes[415] = "Unsupported Media Type", $scope.statusCodes[416] = "Requested Range Not Satisfiable", $scope.statusCodes[417] = "Expectation Failed", $scope.statusCodes[418] = "I'm a teapot", $scope.statusCodes[422] = "Unprocessable Entity", $scope.statusCodes[423] = "Locked", $scope.statusCodes[424] = "Failed Dependency", $scope.statusCodes[426] = "Upgrade Required", $scope.statusCodes[428] = "Precondition Required", $scope.statusCodes[429] = "Too Many Requests", $scope.statusCodes[431] = "Request Header Fields Too Large", $scope.statusCodes[500] = "Internal Server Error", $scope.statusCodes[501] = "Not Implemented", $scope.statusCodes[502] = "Bad Gateway", $scope.statusCodes[503] = "Service Unavailable", $scope.statusCodes[504] = "Gateway Timeout", $scope.statusCodes[505] = "HTTP Version Not Supported", ensureLinksHaveTargetInApiIntro(), setGlobalCollapsedState($localStorage.globalCollapsedState), $scope.hierarchyOpen = !0, $scope.$watch("location.hash()", function (newValue, oldValue) {
        oldValue != newValue && setView(newValue)
    }), $http.jsonp($scope.restBase + "version?hash=" + $scope.licenceHash + "&version=" + $scope.miredotVersion + "&licencetype=" + ($scope.licenceType || "FREE") + "&callback=JSON_CALLBACK").success(function (data) {
        $scope.versionCheckResult = data.upToDate ? "" : " | New version available: " + data.version
    }), $scope.formatTypeValue = function (typeValue) {
        switch (typeValue.type) {
            case"collection":
                return "[ " + $scope.formatTypeValue(typeValue.typeValue) + " ]";
            case"enum":
                return "enum";
            default:
                return typeValue.typeValue
        }
    }, $scope.formatDefaultValue = function (typeValue, defaultValue) {
        switch (typeValue.type) {
            case"enum":
                var enumValues = enumArrayToString(typeValue.values);
                return enumValues = enumValues.replace(defaultValue, '<span class="default" title="Default value">' + defaultValue + "</span>"), enumValues + enumComment(typeValue.values);
            default:
                return void 0 != defaultValue ? '<span class="default" title="Default value">' + defaultValue + "</span>" : ""
        }
    }, $scope.toggleJsonDoc = function (anchor) {
        $scope.jsonDocConfig.hidden = !$scope.jsonDocConfig.hidden, $timeout(function () {
            $location.hash(anchor), $anchorScroll()
        })
    };
    var interfacesByHash = {};
    _.each($scope.interfaces, function (iface) {
        interfacesByHash[iface.hash] = iface
    }), $scope.serviceTags = function () {
        var tagNames = [];
        return _.each($scope.interfaces, function (currentInterface) {
            _.each(currentInterface.tags, function (tagName) {
                tagNames.push(tagName)
            })
        }), tagNames = _.uniq(tagNames), _.map(tagNames, function (tagName) {
            return {name: tagName, selected: !1}
        })
    }(), $scope.isServiceTagSelected = function (tagName) {
        var tag = _.find($scope.serviceTags, function (serviceTag) {
            return serviceTag.name === tagName
        });
        return tag.selected
    }, $scope.projectWarningsByType = function () {
        var result = {};
        return _.each($scope.projectWarnings, function (projectWarning) {
            result[projectWarning.category] = result[projectWarning.category] || [], result[projectWarning.category].push(projectWarning)
        }), result
    }(), $scope.resourceTree = splitPaths(com.qmino.miredot.restApiSource.interfaces), $scope.getFirstLeaf = function (resource) {
        var orderBy = $filter("orderBy");
        if (resource.leafResources.length > 0) {
            var orderedLeafResources = orderBy(resource.leafResources, "url");
            return orderedLeafResources[0]
        }
        return $scope.getFirstLeaf(orderBy(resource.resources, "name")[0])
    }, $scope.openResource = function (url, hash) {
        $scope.currentResource = findParent(url), window.location = "#" + hash
    }, $scope.currentResource = void 0 !== $scope.interfaces && null !== $scope.interfaces && $scope.interfaces.length > 0 ? findParent($scope.getFirstLeaf($scope.resourceTree[0]).url) : null, $scope.currentResourceSet = function () {
        return $scope.singlePage ? $scope.interfaces : _.filter($scope.interfaces, function (el) {
            return _.find($scope.currentResource.leafResources, function (leaf) {
                return leaf.url == el.url
            })
        })
    }, $scope.isResourceVisible = function (url) {
        return $scope.singlePage ? !1 : _.find($scope.currentResource.leafResources, function (leaf) {
            return leaf.url === url
        })
    }, $scope.isComplexObject = function (type) {
        return angular.isObject(type)
    }, $scope.getInterfaceByHash = function (interfaceHash) {
        return interfacesByHash[interfaceHash]
    }, $scope.interfaceHttpOrderFunction = function (iface) {
        return _.indexOf($scope.httpMethods, iface.http)
    }, $scope.methodHttpOrderFunction = function (method) {
        return _.indexOf($scope.httpMethods, method.method)
    }, $scope.httpMethods = ["GET", "HEAD", "PUT", "POST", "DELETE"], $scope.toggleSearchQueryHttp = function (http) {
        $scope.searchQuery.http = $scope.searchQuery.http === http ? "" : http
    }, $scope.setGlobalCollapsedState = setGlobalCollapsedState, $scope.collapseTree = function () {
        collapseResources($scope.resourceTree, !1)
    }, $scope.expandTree = function () {
        collapseResources($scope.resourceTree, !0)
    }, setView($location.hash()), void 0 === com.qmino.miredot.restApiSource.initialCollapseLevel || null === com.qmino.miredot.restApiSource.initialCollapseLevel ? countResources($scope.resourceTree, null) > 35 ? ($scope.collapseTree(), collapseResources($scope.resourceTree, !1, 0)) : ($scope.expandTree(), collapseResources($scope.resourceTree, !0, 0)) : ($scope.collapseTree(), collapseResources($scope.resourceTree, !0, 0)), createSearchIndex(), $scope.doFullTextSearch = function () {
        if ("" === $scope.fullTextSearch)searchResults = null; else {
            searchResults = [];
            var results = searchIdx.search($scope.fullTextSearch);
            _.each(results, function (result) {
                searchResults.push(searchDocs[result.ref].iface)
            })
        }
    }
}
function parseKeyValue(keyValue) {
    var key_value, key, obj = {};
    return angular.forEach((keyValue || "").split("&"), function (keyValue) {
        keyValue && (key_value = keyValue.split("="), key = decodeURIComponent(key_value[0]), obj[key] = angular.isDefined(key_value[1]) ? decodeURIComponent(key_value[1]) : !0)
    }), obj
}
function enumArrayToString(enumArray) {
    for (var output = "", i = 0; i < enumArray.length; i++)0 != i && (output += " | "), output += enumArray[i].name;
    return output
}
function escapeHtml(value) {
    return value.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;")
}
function getNextEnumId() {
    return "enum_" + counter++
}
function enumComment(enumArray) {
    var comment = "";
    return _.each(enumArray, function (value) {
        null !== value.comment && (comment += "<strong>" + value.name + ":</strong> " + value.comment + "<br/>")
    }), "" === comment ? "" : ' <span class="propertyComment, border" data-container="body" data-toggle="popover" data-trigger="hover" popover data-placement="right" id="' + getNextEnumId() + '" data-content="' + escapeHtml(comment) + '"><span>...</span></span>'
}
function hideAfterTimeout(elem) {
    setTimeout(function () {
        $(".popover:hover").length || $("#" + elem.attr("id") + ":hover").length || elem.popover("hide")
    }, 250)
}
function collectAllComments(to, history) {
    history = history || [];
    var comment = "";
    switch (void 0 !== to.name && null !== to.name && (comment += to.name), void 0 !== to.comment && null !== to.comment && (comment += ("" === comment ? "" : ": ") + $("<div>" + to.comment + "</div>").text()), void 0 !== to.fullComment && null !== to.fullComment && (comment += "\n" + $("<div>" + to.fullComment + "</div>").text()), to.typeValue.type) {
        case"simple":
            break;
        case"enum":
            _.each(to.typeValue.values, function (value) {
                comment += "\n" + value.name + (null !== value.comment ? ": " + value.comment : "")
            });
            break;
        case"collection":
            comment += "\n" + collectAllComments(to.typeValue, history);
            break;
        case"map":
            comment += "\n" + collectAllComments(to.typeValue, history);
            break;
        case"abstract":
            if (-1 === _.indexOf(history, to.typeValue.name)) {
                var newHistory = history.slice(0);
                newHistory.push(to.typeValue.name), _.each(to.typeValue.subTypes, function (subType) {
                    _.each(subType.to.content, function (field) {
                        comment += "\n" + collectAllComments(field, newHistory)
                    })
                })
            }
            break;
        case"complex":
            if (-1 === _.indexOf(history, to.typeValue.name)) {
                var newHistory = history.slice(0);
                newHistory.push(to.typeValue.name), _.each(to.typeValue.content, function (field) {
                    comment += "\n" + collectAllComments(field, newHistory)
                })
            }
    }
    return comment
}
function createSearchIndex() {
    searchIdx = lunr(function () {
        this.field("url", {boost: 1}), this.field("title", {boost: 1}), this.field("comment", {boost: 1}), this.field("body", {boost: 1}), this.field("output", {boost: 1}), this.field("params", {boost: 1}), this.field("statusCodes", {boost: 1})
    }), searchIdx.pipeline.reset(), searchIdx.pipeline.add(lunr.stemmer);
    var idx = 0;
    _.each(com.qmino.miredot.restApiSource.interfaces, function (iface) {
        var doc = {
            id: idx,
            iface: iface,
            url: iface.url.replace(/\//g, " "),
            title: iface.title,
            comment: $("<div>" + iface.beschrijving + "</div>").text(),
            body: "",
            output: "",
            params: "",
            statusCodes: ""
        };
        ++idx, _.each(iface.inputs.PATH, function (param) {
            doc.params += collectAllComments(param) + "\n"
        }), _.each(iface.inputs.QUERY, function (param) {
            doc.params += collectAllComments(param) + "\n"
        }), _.each(iface.inputs.HEADER, function (param) {
            doc.params += collectAllComments(param) + "\n"
        }), _.each(iface.inputs.COOKIE, function (param) {
            doc.params += collectAllComments(param) + "\n"
        }), _.each(iface.inputs.MATRIX, function (param) {
            doc.params += collectAllComments(param) + "\n"
        }), _.each(iface.inputs.BODY, function (param) {
            doc.body += collectAllComments(param) + "\n"
        }), void 0 !== iface.output.typeValue && (doc.output += collectAllComments(iface.output)), _.each(iface.statusCodes, function (statusCode) {
            doc.statusCodes += statusCode.httpCode, null !== statusCode.comment && void 0 !== statusCode.comment && (doc.statusCodes += ": " + statusCode.comment), doc.statusCodes += "\n"
        }), searchDocs.push(doc)
    }), _.each(searchDocs, function (doc) {
        searchIdx.add(doc)
    })
}
angular.module("miredot", ["miredot.filters", "miredot.directives", "ui.bootstrap.buttons", "watchFighers", "ngStorage"]).config(["$compileProvider", "$locationProvider", function ($compileProvider, $locationProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|tel):/), $locationProvider.html5Mode(!0)
}]);
var counter = 0;
String.prototype.stripTrailingSlash = function () {
    return "/" === this.substr(-1) ? this.substr(0, this.length - 1) : this
}, String.prototype.ensureStartsWithSlash = function () {
    return "/" !== this.substr(0, 1) ? "/" + this : this
}, angular.module("miredot.directives", []), angular.module("miredot.directives").directive("jsonTo", function ($compile) {
    function getNewId() {
        return _idCount++
    }

    function getHighlightHtml(to) {
        var html = "", id = to.__md_id || to.__sub_id, addHoverClass = "onmouseover=\"$('#" + id + "').addClass('highlightJsonRecursive');\"", removeHoverClass = "onmouseout=\"$('#" + id + "').removeClass('highlightJsonRecursive');\"";
        return html += '<a href="#' + id + '_a" ' + addHoverClass + " " + removeHoverClass + ' class="recursionLink" target="_self" title="recursion">', html += '<i class="icon-retweet"></i>', html += "</a>"
    }

    var _idCount = 0;
    return {
        restrict: "E",
        transclude: !1,
        scope: {to: "=", jsonDocConfig: "=", toggleJsonDoc: "="},
        link: function (scope, element) {
            function buildComment(comment, fullComment) {
                var result = "";
                if (scope.jsonDocConfig.enabled && comment) {
                    if (result += '<span class="propertyComment" ng-show="!jsonDocConfig.hidden">', result += comment, null !== fullComment && void 0 !== fullComment) {
                        var id = "full_" + getNewId();
                        result += ' <span class="propertyComment, border" data-toggle="popover" id="' + id + '" popover data-placement="bottom" data-content="' + escapeHtml(fullComment) + '"><span>...</span></span>'
                    }
                    result += "</span>"
                }
                return result
            }

            function buildSubTypeSwitcher(to, beforeFieldComment, afterFieldComment) {
                var html = "";
                return html += '<li class="parameterItem">', to.property && (html += '<span class="parameterName">' + to.property + ":</span>"), html += beforeFieldComment, html += buildComment(to.propertyComment, to.fullComment), html += "</li>", html += afterFieldComment
            }

            function buildToProperties(to, history) {
                function createSubTypeButton(subType) {
                    return '<div class="btn" ng-model="$parent.' + subTypeModel + '" btn-radio="\'' + subType.to.__sub_id + "'\">" + subType.name + "</div>"
                }

                function appendSubTypeChooser() {
                    html += '<span ng-show="jsonDocConfig.hidden">';
                    var beforeFieldComment = '<span class="parameterType"><div class="btn-group">';
                    _.each(to.subTypes, function (subType) {
                        beforeFieldComment += createSubTypeButton(subType)
                    }), beforeFieldComment += "</div></span>", html += buildSubTypeSwitcher(to, beforeFieldComment, ""), html += "</span>", html += '<span ng-show="!jsonDocConfig.hidden">';
                    var afterFieldComment = '<span class="subTypeSwitch">';
                    afterFieldComment += '<span class="btn-group-vertical">', _.each(to.subTypes, function (subType) {
                        afterFieldComment += "<span>", afterFieldComment += createSubTypeButton(subType), subType.comment && (afterFieldComment += '<span class="propertyComment">', afterFieldComment += subType.comment, afterFieldComment += "</span>"), afterFieldComment += "</span>"
                    }), afterFieldComment += "</span>", afterFieldComment += "</span>", html += buildSubTypeSwitcher(to, "", afterFieldComment), html += "</span>"
                }

                function appendProperty(field) {
                    html += '<li class="parameterItem">', html += '<span class="parameterName">' + field.name + ":</span>", html += build(field.typeValue, field.comment, field.fullComment, history), html += "</li>"
                }

                var html = "", subTypeModel = null;
                return "abstract" === to.type && (subTypeModel = "subTypeModel" + getNewId(), _.each(to.subTypes, function (subType) {
                    subType.to.__sub_id = "md_to_" + getNewId()
                }), scope[subTypeModel] = to.subTypes[0].to.__sub_id), to.subTypes ? _.each(to.subTypes, function (subType) {
                    html += '<a id="' + subType.to.__sub_id + '_a" class="anchor"></a>', html += '<div ng-if="' + subTypeModel + " == '" + subType.to.__sub_id + '\'" id="' + subType.to.__sub_id + '">', subType.to.ordered || appendSubTypeChooser(), _.each(subType.to.content, function (field) {
                        field.name === to.property ? subType.to.ordered && appendSubTypeChooser() : appendProperty(field)
                    }), html += "</div>"
                }) : _.each(to.content, function (field) {
                    appendProperty(field)
                }), html
            }

            var build = function (to, comment, fullComment, history) {
                history = history || [];
                var newHistory, html = "";
                switch (to.type) {
                    case"simple":
                        html += '<span class="parameterType">', html += to.typeValue, html += "</span>", html += buildComment(comment, fullComment);
                        break;
                    case"enum":
                        html += '<span class="parameterType">', html += enumArrayToString(to.values) + enumComment(to.values), html += "</span>", html += buildComment(comment, fullComment);
                        break;
                    case"collection":
                        html += "<span>[</span>", html += buildComment(comment, fullComment), html += '<ul class="toContainer"><li class="parameterItem">', html += build(to.typeValue, to.comment, to.fullComment, history), html += "</li></ul>", html += "<span>]</span>";
                        break;
                    case"map":
                        html += "<span>{</span>", html += buildComment(comment, fullComment), html += '<ul class="toContainer"><li class="parameterItem">', html += '<span class="parameterType">string</span> =>', html += build(to.typeValue, to.comment, to.fullComment, history), html += "</li></ul>", html += "<span>}</span>";
                        break;
                    default:
                        _.indexOf(history, to.name) >= 0 ? (html += getHighlightHtml(to), html += buildComment(comment, fullComment)) : (newHistory = history.slice(0), newHistory.push(to.name), to.__md_id = "md_to_" + getNewId(), html += buildComment(comment, fullComment), html += '<a id="' + to.__md_id + '_a" class="anchor"></a>', html += '<div id="' + to.__md_id + '">', html += "<span>{</span>", html += '<ul class="toContainer">', html += buildToProperties(to, newHistory), html += "</ul>", html += "<span>}</span>", html += "</div>")
                }
                return html
            }, anchorName = "json_" + getNewId(), anchorHtml = '<a name="' + anchorName + '"/>', togglePropertyCommentsHtml = "";
            scope.jsonDocConfig.enabled && (togglePropertyCommentsHtml += '<span class="togglePropertyComments" ng-click="toggleJsonDoc(\'' + anchorName + '\')"><span ng-show="jsonDocConfig.hidden">Show</span><span ng-show="!jsonDocConfig.hidden">Hide</span> descriptions</span>');
            var newElement = angular.element(anchorHtml + togglePropertyCommentsHtml + build(scope.to));
            $compile(newElement)(scope), element.replaceWith(newElement)
        }
    }
}).directive("widthonblur", function () {
    return function (scope, element, attrs) {
        element.css("width", attrs.widthonblur), element.bind("blur", function () {
            element.css("width", attrs.widthonblur)
        })
    }
}).directive("widthonfocus", function () {
    return function (scope, element, attrs) {
        element.bind("focus", function () {
            element.css("width", attrs.widthonfocus)
        })
    }
}).directive("onFocus", ["$parse", function ($parse) {
    return function (scope, element, attr) {
        var fn = $parse(attr.onFocus);
        element.bind("focus", function (event) {
            scope.$apply(function () {
                fn(scope, {$event: event})
            })
        })
    }
}]).directive("onBlur", ["$parse", function ($parse) {
    return function (scope, element, attr) {
        var fn = $parse(attr.onBlur);
        element.bind("blur", function (event) {
            scope.$apply(function () {
                fn(scope, {$event: event})
            })
        })
    }
}]).directive("focusWhen", function ($parse, $timeout) {
    return function (scope, element, attr) {
        scope.$watch(attr.focusWhen, function (newValue, oldValue) {
            !oldValue && newValue && $timeout(function () {
                if (element.focus(), element.val()) {
                    var tmpStr = element.val();
                    element.val(""), element.val(tmpStr)
                }
            })
        }, !0)
    }
}).directive("onEnter", function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            13 === event.which && (scope.$apply(function () {
                scope.$eval(attrs.onEnter)
            }), event.preventDefault())
        })
    }
}).directive("popover", function () {
    return function (scope, elem) {
        elem.popover({trigger: "manual", html: !0}).on("mouseenter", function () {
            $(this).popover("show"), $(".popover").on("mouseleave", function () {
                hideAfterTimeout(elem)
            }).on("click", function () {
                elem.popover("hide")
            })
        }).on("mouseleave", function () {
            hideAfterTimeout(elem)
        })
    }
}), angular.module("miredot.filters", ["ngSanitize", "ui.filters"]).filter("formatUrlParams", function () {
    return function (value) {
        return value ? value.toString().replace(new RegExp("{(.*?)}", "gi"), '<span class="paramName">$1</span>') : value
    }
}).filter("capitaliseFirstLetter", function () {
    return function (string) {
        return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase()
    }
}).filter("arraySort", function () {
    return function (input) {
        return input.sort()
    }
}).filter("searchByExampleFilter", function () {
    function getRegExp(iface) {
        var url = iface.url.stripTrailingSlash();
        url = "^" + url + "$";
        for (var paramMatch, regex = "", re = /{([^\}]+)}/g, lastMatchedIndex = 0; null !== (paramMatch = re.exec(url));) {
            regex += url.slice(lastMatchedIndex, paramMatch.index);
            var pathParam = paramMatch[1];
            if (pathParam.indexOf(":") > 0) {
                var paramRegex = jQuery.trim(pathParam.split(":")[1]);
                regex += "(" + paramRegex + ")"
            } else regex += "([^\\/]*)";
            lastMatchedIndex = re.lastIndex
        }
        return regex += url.substr(lastMatchedIndex), new RegExp(regex, "i")
    }

    function searchByExampleFilter(searchString) {
        return function (currentInterface, baseUrl) {
            if (0 === searchString.length)return !0;
            var split = searchString.split("?"), locationPart = split[0], queryPart = split[1];
            if (angular.isDefined(queryPart)) {
                var queryParams = parseKeyValue(queryPart), valid = !0;
                if (angular.forEach(currentInterface.inputs.QUERY, function (queryParam) {
                        valid = valid && (valid = angular.isDefined(queryParams[queryParam.name.toLowerCase()]))
                    }), !valid)return !1
            }
            var search = locationPart.replace(baseUrl, "").stripTrailingSlash().ensureStartsWithSlash();
            return currentInterface.regexp = currentInterface.regexp || getRegExp(currentInterface), currentInterface.regexp.test(search)
        }
    }

    return function (iface, searchString, baseUrl) {
        return searchByExampleFilter(searchString)(iface, baseUrl)
    }
}).filter("filterBySearchQuery", function () {
    function recursiveMatch(searchQuery) {
        return function (resource) {
            if (0 === searchQuery.url.length)return !0;
            if (resource.name.toLowerCase().indexOf(searchQuery.url.toLowerCase()) > -1)return !0;
            for (var i = 0; i < resource.leafResources.length; i++)if (resource.leafResources[i].url.toLowerCase().indexOf(searchQuery.url.toLowerCase()) > -1)return !0;
            for (var j = 0; j < resource.resources.length; j++)if (recursiveMatch(searchQuery)(resource.resources[j]))return !0;
            return !1
        }
    }

    return function (resource, searchQuery) {
        return recursiveMatch(searchQuery)(resource)
    }
}).filter("serviceTagFilter", function () {
    function matchesServiceTags(serviceTags) {
        return function (currentInterface) {
            return _.every(serviceTags, function (serviceTag) {
                return serviceTag.selected ? _.contains(currentInterface.tags, serviceTag.name) : !0
            })
        }
    }

    return function (currentInterface, serviceTags) {
        return matchesServiceTags(serviceTags)(currentInterface)
    }
}).filter("searchQueryFilter", function () {
    function matchesSearchQuery(searchQuery) {
        return function (currentInterface) {
            return searchQuery.http && currentInterface.http.indexOf(searchQuery.http) < 0 ? !1 : searchQuery.url && currentInterface.url.toLowerCase().indexOf(searchQuery.url.toLowerCase()) < 0 ? !1 : !0
        }
    }

    return function (currentInterface, searchQuery) {
        return matchesSearchQuery(searchQuery)(currentInterface)
    }
}).filter("fullTextSearchFilter", function () {
    return function (currentInterface) {
        return void 0 === searchResults || null === searchResults ? !0 : -1 !== searchResults.indexOf(currentInterface)
    }
}), function () {
    function _storageFactory(storageType) {
        return ["$rootScope", "$browser", "$window", function ($rootScope, $browser, $window) {
            var _last$storage, webStorage = $window[storageType], $storage = {
                $default: function (items) {
                    for (var k in items)angular.isDefined($storage[k]) || ($storage[k] = items[k]);
                    return $storage
                }, $reset: function (items) {
                    for (var k in $storage)"$" === k[0] || delete $storage[k];
                    return $storage.$default(items)
                }
            };
            webStorage = webStorage || {
                setItem: function () {
                }, getItem: function () {
                }, removeItem: function () {
                }
            };
            for (var k, i = 0; i < webStorage.length && (k = webStorage.key(i)); i++)"ngStorage-" === k.slice(0, 10) && ($storage[k.slice(10)] = angular.fromJson(webStorage.getItem(k)));
            return _last$storage = angular.copy($storage), $browser.addPollFn(function () {
                if (!angular.equals($storage, _last$storage)) {
                    angular.forEach($storage, function (v, k) {
                        angular.isDefined(v) && "$" !== k[0] && ($storage[k] = angular.fromJson(angular.toJson(v)), webStorage.setItem("ngStorage-" + k, angular.toJson(v))), delete _last$storage[k]
                    });
                    for (var k in _last$storage)webStorage.removeItem("ngStorage-" + k);
                    _last$storage = angular.copy($storage), $rootScope.$apply()
                }
            }), "localStorage" === storageType && $window.addEventListener && $window.addEventListener("storage", function (event) {
                "ngStorage-" === event.key.slice(0, 10) && (event.newValue ? $storage[event.key.slice(10)] = angular.fromJson(event.newValue) : delete $storage[event.key.slice(10)], _last$storage = angular.copy($storage), $rootScope.$apply())
            }), $storage
        }]
    }

    angular.module("ngStorage", []).factory("$localStorage", _storageFactory("localStorage")).factory("$sessionStorage", _storageFactory("sessionStorage"))
}();
var searchDocs = [], searchIdx = null, searchResults = null;
angular.module("watchFighers", []).directive("setIf", [function () {
    return {
        transclude: "element",
        priority: 1e3,
        terminal: !0,
        restrict: "A",
        compile: function (element, attr, linker) {
            return function (scope, iterStartElement, attr) {
                iterStartElement[0].doNotMove = !0;
                var expression = attr.setIf, value = scope.$eval(expression);
                value && linker(scope, function (clone) {
                    iterStartElement.after(clone)
                })
            }
        }
    }
}]).directive("setHtml", function ($compile) {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            $($el).html($scope.$eval($attr.setHtml) || ""), $compile($el.contents())($scope)
        }
    }
}).directive("setText", function () {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            $($el).text($scope.$eval($attr.setText) || "")
        }
    }
}).directive("setClass", function () {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            function setClass(attributeValue, $scope, $el) {
                if (attributeValue.indexOf(":") > 0) {
                    var classNameCondition = attributeValue.split(":", 2), className = jQuery.trim(classNameCondition[0]), condition = $scope.$eval(jQuery.trim(classNameCondition[1]));
                    condition && $($el).addClass(className)
                } else $($el).addClass($scope.$eval(attributeValue) || "")
            }

            $attr.setClass.indexOf(",") > 0 ? _.each($attr.setClass.split(","), function (attributeValue) {
                setClass(jQuery.trim(attributeValue), $scope, $el)
            }) : setClass($attr.setClass, $scope, $el)
        }
    }
}).directive("setTitle", function () {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            $($el).attr("title", $scope.$eval($attr.setTitle) || "")
        }
    }
}).directive("setHref", function () {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            $($el).attr("href", $scope.$eval($attr.setHref) || "")
        }
    }
}).directive("setId", function () {
    return {
        restrict: "A", priority: 100, link: function ($scope, $el, $attr) {
            $($el).attr("id", $scope.$eval($attr.setId) || "")
        }
    }
});