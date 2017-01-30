angular.
module('core.budget').
factory('BudgetList', ['$resource',
    function($resource) {
        return $resource('budget/list', {}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }
]);