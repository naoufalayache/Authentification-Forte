import { TestBed } from '@angular/core/testing';

import { Coockie } from './coockie';

describe('Coockie', () => {
  let service: Coockie;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Coockie);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
