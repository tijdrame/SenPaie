/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { RegimeDetailComponent } from '../../../../../../main/webapp/app/entities/regime/regime-detail.component';
import { RegimeService } from '../../../../../../main/webapp/app/entities/regime/regime.service';
import { Regime } from '../../../../../../main/webapp/app/entities/regime/regime.model';

describe('Component Tests', () => {

    describe('Regime Management Detail Component', () => {
        let comp: RegimeDetailComponent;
        let fixture: ComponentFixture<RegimeDetailComponent>;
        let service: RegimeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RegimeDetailComponent],
                providers: [
                    RegimeService
                ]
            })
            .overrideTemplate(RegimeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegimeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Regime(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.regime).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
