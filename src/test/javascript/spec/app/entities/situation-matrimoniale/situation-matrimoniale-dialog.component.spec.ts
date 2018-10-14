/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { SituationMatrimonialeDialogComponent } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale-dialog.component';
import { SituationMatrimonialeService } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.service';
import { SituationMatrimoniale } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.model';

describe('Component Tests', () => {

    describe('SituationMatrimoniale Management Dialog Component', () => {
        let comp: SituationMatrimonialeDialogComponent;
        let fixture: ComponentFixture<SituationMatrimonialeDialogComponent>;
        let service: SituationMatrimonialeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [SituationMatrimonialeDialogComponent],
                providers: [
                    SituationMatrimonialeService
                ]
            })
            .overrideTemplate(SituationMatrimonialeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SituationMatrimonialeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SituationMatrimonialeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SituationMatrimoniale(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.situationMatrimoniale = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'situationMatrimonialeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SituationMatrimoniale();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.situationMatrimoniale = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'situationMatrimonialeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
