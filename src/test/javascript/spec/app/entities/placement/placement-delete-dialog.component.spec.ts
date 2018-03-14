/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MybizdevTestModule } from '../../../test.module';
import { PlacementDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/placement/placement-delete-dialog.component';
import { PlacementService } from '../../../../../../main/webapp/app/entities/placement/placement.service';

describe('Component Tests', () => {

    describe('Placement Management Delete Component', () => {
        let comp: PlacementDeleteDialogComponent;
        let fixture: ComponentFixture<PlacementDeleteDialogComponent>;
        let service: PlacementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [PlacementDeleteDialogComponent],
                providers: [
                    PlacementService
                ]
            })
            .overrideTemplate(PlacementDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlacementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlacementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
