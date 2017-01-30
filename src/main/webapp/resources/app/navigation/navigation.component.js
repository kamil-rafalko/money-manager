angular.
module('navigation').
component('navigation', {
    templateUrl: 'app/navigation/navigation.template.html',
    controller: ['$rootScope', '$location', '$http',
        function NavigationController($rootScope, $location, $http) {
            this.logout = function () {
                $http.post('logout', {}).finally(function () {
                    $rootScope.authenticated = false;
                    $location.path("/login");
                })
            };
        }
    ]
});