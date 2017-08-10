'use strict';

require('angular-route');
require('./../core/budget/budget.module');

angular.module('budget', [
    'ngRoute',
    'core.budget'
]);

require('./budget-list.component');
require('./budget.component');
