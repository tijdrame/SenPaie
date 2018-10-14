/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { BaremeDetailComponent } from '../../../../../../main/webapp/app/entities/bareme/bareme-detail.component';
import { BaremeService } from '../../../../../../main/webapp/app/entities/bareme/bareme.service';
import { Bareme } from '../../../../../../main/webapp/app/entities/bareme/bareme.model';

describe('Component Tests', () => {

    describe('Bareme Management Detail Component', () => {
        let comp: BaremeDetailComponent;
        let fixture: ComponentFixture<BaremeDetailComponent>;
        let service: BaremeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [BaremeDetailComponent],
                providers: [
                    BaremeService
                ]
            })
            .overrideTemplate(BaremeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BaremeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaremeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bareme(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bareme).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
