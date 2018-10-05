/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { ConventionDetailComponent } from '../../../../../../main/webapp/app/entities/convention/convention-detail.component';
import { ConventionService } from '../../../../../../main/webapp/app/entities/convention/convention.service';
import { Convention } from '../../../../../../main/webapp/app/entities/convention/convention.model';

describe('Component Tests', () => {

    describe('Convention Management Detail Component', () => {
        let comp: ConventionDetailComponent;
        let fixture: ComponentFixture<ConventionDetailComponent>;
        let service: ConventionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [ConventionDetailComponent],
                providers: [
                    ConventionService
                ]
            })
            .overrideTemplate(ConventionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConventionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConventionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Convention(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.convention).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
