/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { RecapDetailComponent } from '../../../../../../main/webapp/app/entities/recap/recap-detail.component';
import { RecapService } from '../../../../../../main/webapp/app/entities/recap/recap.service';
import { Recap } from '../../../../../../main/webapp/app/entities/recap/recap.model';

describe('Component Tests', () => {

    describe('Recap Management Detail Component', () => {
        let comp: RecapDetailComponent;
        let fixture: ComponentFixture<RecapDetailComponent>;
        let service: RecapService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RecapDetailComponent],
                providers: [
                    RecapService
                ]
            })
            .overrideTemplate(RecapDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecapDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecapService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Recap(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.recap).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
