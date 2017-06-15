angular.
module('core.transaction').
factory('Transaction', ['$resource',
    function($resource) {
        return $resource('budget/:id/transactions');
    }
]);