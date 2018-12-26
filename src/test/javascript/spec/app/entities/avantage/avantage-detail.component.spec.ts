/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { AvantageDetailComponent } from '../../../../../../main/webapp/app/entities/avantage/avantage-detail.component';
import { AvantageService } from '../../../../../../main/webapp/app/entities/avantage/avantage.service';
import { Avantage } from '../../../../../../main/webapp/app/entities/avantage/avantage.model';

describe('Component Tests', () => {

    describe('Avantage Management Detail Component', () => {
        let comp: AvantageDetailComponent;
        let fixture: ComponentFixture<AvantageDetailComponent>;
        let service: AvantageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AvantageDetailComponent],
                providers: [
                    AvantageService
                ]
            })
            .overrideTemplate(AvantageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvantageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvantageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Avantage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.avantage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
