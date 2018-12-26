/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { PrimeDetailComponent } from '../../../../../../main/webapp/app/entities/prime/prime-detail.component';
import { PrimeService } from '../../../../../../main/webapp/app/entities/prime/prime.service';
import { Prime } from '../../../../../../main/webapp/app/entities/prime/prime.model';

describe('Component Tests', () => {

    describe('Prime Management Detail Component', () => {
        let comp: PrimeDetailComponent;
        let fixture: ComponentFixture<PrimeDetailComponent>;
        let service: PrimeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PrimeDetailComponent],
                providers: [
                    PrimeService
                ]
            })
            .overrideTemplate(PrimeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrimeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrimeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Prime(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prime).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
