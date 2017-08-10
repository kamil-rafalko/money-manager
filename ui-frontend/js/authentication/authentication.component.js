angular.
    module('authentication').
    component('authentication', {
        template: require('./authentication.template.html'),
        scope: true,
        controller: ['$rootScope', '$http', '$location', 
            function AuthenticationController($rootScope, $http, $location) {
                var self = this;

                var authenticate = function (credentials, callback) {
                    var headers = credentials ? {
                        authorization: "Basic " + btoa(credentials.username + ":"
                            + credentials.password)
                    } : {};

                    $http.get('api/user', {headers: headers}).then(function (response) {
                        $rootScope.authenticated = !!response.data.name;
                        if ($rootScope.authenticated) {
                            $location.path("/");
                        }
                        callback && callback();
                    }, function () {
                        $rootScope.authenticated = false;
                        callback && callback();
                    });
                };

                authenticate();
                self.credentials = {};
                self.login = function () {
                    authenticate(self.credentials, function () {
                        self.error = !$rootScope.authenticated;
                        $location.path("/");
                    })
                };
            }
        ]
    });
