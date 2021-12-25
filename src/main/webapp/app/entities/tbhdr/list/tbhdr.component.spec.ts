import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBHDRService } from '../service/tbhdr.service';

import { TBHDRComponent } from './tbhdr.component';

describe('TBHDR Management Component', () => {
  let comp: TBHDRComponent;
  let fixture: ComponentFixture<TBHDRComponent>;
  let service: TBHDRService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBHDRComponent],
    })
      .overrideTemplate(TBHDRComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBHDRComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBHDRService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ thno: 123 }],
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
    expect(comp.tBHDRS?.[0]).toEqual(expect.objectContaining({ thno: 123 }));
  });
});
