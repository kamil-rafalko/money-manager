angular.
module('core.transaction').
factory('Transaction', ['$resource',
    function($resource) {
        return $resource('api/budgets/:id/transactions');
    }
]);