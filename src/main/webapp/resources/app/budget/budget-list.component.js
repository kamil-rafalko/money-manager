angular.
module('budget').
component('budgetList', {
    templateUrl: 'app/budget/budget-list.template.html',
    controller: ['BudgetList', '$routeParams',
        function BudgetListController(BudgetList, $routeParams) {
            this.budgets = BudgetList.query();
            this.currentId = $routeParams.id;
        }
    ]
});