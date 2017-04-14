'use strict';

angular.module('money-manager', [
    'ngRoute',
    'budget',
    'authentication',
    'core.budget',
    'core.transaction',
    'navigation',
    'transaction'
]);

angular.module('money-manager')
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/login', {
            template: '<authentication></authentication>'
        }).when('/', {
            template: '<budget></budget>'
        }).when('/budget/:id', {
            template: '<budget></budget>'
        });

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    });
