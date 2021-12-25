import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBCOMFOService } from '../service/tbcomfo.service';

import { TBCOMFOComponent } from './tbcomfo.component';

describe('TBCOMFO Management Component', () => {
  let comp: TBCOMFOComponent;
  let fixture: ComponentFixture<TBCOMFOComponent>;
  let service: TBCOMFOService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBCOMFOComponent],
    })
      .overrideTemplate(TBCOMFOComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBCOMFOComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBCOMFOService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
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
    expect(comp.tBCOMFOS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
