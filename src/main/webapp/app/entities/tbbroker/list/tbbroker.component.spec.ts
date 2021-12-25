import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBBROKERService } from '../service/tbbroker.service';

import { TBBROKERComponent } from './tbbroker.component';

describe('TBBROKER Management Component', () => {
  let comp: TBBROKERComponent;
  let fixture: ComponentFixture<TBBROKERComponent>;
  let service: TBBROKERService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBBROKERComponent],
    })
      .overrideTemplate(TBBROKERComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBBROKERComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBBROKERService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ brcode: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tBBROKERS?.[0]).toEqual(expect.objectContaining({ brcode: 'ABC' }));
  });
});
