import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBPROVService } from '../service/tbprov.service';

import { TBPROVComponent } from './tbprov.component';

describe('TBPROV Management Component', () => {
  let comp: TBPROVComponent;
  let fixture: ComponentFixture<TBPROVComponent>;
  let service: TBPROVService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBPROVComponent],
    })
      .overrideTemplate(TBPROVComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBPROVComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBPROVService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ provcod: 'ABC' }],
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
    expect(comp.tBPROVS?.[0]).toEqual(expect.objectContaining({ provcod: 'ABC' }));
  });
});
