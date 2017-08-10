'use strict';

require('bootstrap-css-only');
require('../stylesheets/main.css');

require('angular');
require('angular-route');

require('./authentication/authentication.module');
require('./budget/budget.module');
require('./navigation/navigation.module');
require('./transaction/transaction.module');
require('./core/transaction/transaction.module');
require('./core/budget/budget.module');

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
