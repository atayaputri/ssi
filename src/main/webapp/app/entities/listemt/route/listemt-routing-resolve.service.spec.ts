jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILISTEMT, LISTEMT } from '../listemt.model';
import { LISTEMTService } from '../service/listemt.service';

import { LISTEMTRoutingResolveService } from './listemt-routing-resolve.service';

describe('LISTEMT routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LISTEMTRoutingResolveService;
  let service: LISTEMTService;
  let resultLISTEMT: ILISTEMT | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(LISTEMTRoutingResolveService);
    service = TestBed.inject(LISTEMTService);
    resultLISTEMT = undefined;
  });

  describe('resolve', () => {
    it('should return ILISTEMT returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLISTEMT = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLISTEMT).toEqual({ id: 123 });
    });

    it('should return new ILISTEMT if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLISTEMT = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLISTEMT).toEqual(new LISTEMT());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LISTEMT })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLISTEMT = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLISTEMT).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
