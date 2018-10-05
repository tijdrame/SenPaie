/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { BulletinComponent } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.component';
import { BulletinService } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.service';
import { Bulletin } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.model';

describe('Component Tests', () => {

    describe('Bulletin Management Component', () => {
        let comp: BulletinComponent;
        let fixture: ComponentFixture<BulletinComponent>;
        let service: BulletinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [BulletinComponent],
                providers: [
                    BulletinService
                ]
            })
            .overrideTemplate(BulletinComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BulletinComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulletinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bulletin(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bulletins[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
