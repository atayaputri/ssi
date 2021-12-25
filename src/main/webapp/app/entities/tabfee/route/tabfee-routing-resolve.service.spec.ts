jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITABFEE, TABFEE } from '../tabfee.model';
import { TABFEEService } from '../service/tabfee.service';

import { TABFEERoutingResolveService } from './tabfee-routing-resolve.service';

describe('TABFEE routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TABFEERoutingResolveService;
  let service: TABFEEService;
  let resultTABFEE: ITABFEE | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TABFEERoutingResolveService);
    service = TestBed.inject(TABFEEService);
    resultTABFEE = undefined;
  });

  describe('resolve', () => {
    it('should return ITABFEE returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABFEE = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTABFEE).toEqual({ id: 123 });
    });

    it('should return new ITABFEE if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABFEE = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTABFEE).toEqual(new TABFEE());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TABFEE })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABFEE = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTABFEE).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
