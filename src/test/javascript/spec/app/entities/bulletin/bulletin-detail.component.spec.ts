/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { BulletinDetailComponent } from '../../../../../../main/webapp/app/entities/bulletin/bulletin-detail.component';
import { BulletinService } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.service';
import { Bulletin } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.model';

describe('Component Tests', () => {

    describe('Bulletin Management Detail Component', () => {
        let comp: BulletinDetailComponent;
        let fixture: ComponentFixture<BulletinDetailComponent>;
        let service: BulletinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [BulletinDetailComponent],
                providers: [
                    BulletinService
                ]
            })
            .overrideTemplate(BulletinDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BulletinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulletinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bulletin(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bulletin).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
