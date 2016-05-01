package com.corriel.budget.entity.fund;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("account")
public class Account extends Fund {
}
