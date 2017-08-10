angular.
module('budget').
component('budgetList', {
    template: require('./budget-list.template.html'),
    controller: ['BudgetList', '$routeParams', '$scope',
        function BudgetListController(BudgetList, $routeParams, $scope) {
            var self = this;
            var initBudgets = function() {
                self.budgets = BudgetList.query();
            };

            initBudgets();
            this.currentId = $routeParams.id;

            $scope.$on('transactionInsert', function() {
                initBudgets();
            });
        }
    ]
});