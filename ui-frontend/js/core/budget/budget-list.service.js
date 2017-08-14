angular.
module('core.budget').
factory('BudgetList', ['$resource',
    function($resource) {
        return $resource('api/budgets', {}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }
]);