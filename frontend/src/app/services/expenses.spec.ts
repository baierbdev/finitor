import { TestBed } from '@angular/core/testing';

import { ExpensesServices } from './expenses';

describe('Expenses', () => {
  let service: ExpensesServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExpensesServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
