/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MybizdevTestModule } from '../../../test.module';
import { PlacementComponent } from '../../../../../../main/webapp/app/entities/placement/placement.component';
import { PlacementService } from '../../../../../../main/webapp/app/entities/placement/placement.service';
import { Placement } from '../../../../../../main/webapp/app/entities/placement/placement.model';

describe('Component Tests', () => {

    describe('Placement Management Component', () => {
        let comp: PlacementComponent;
        let fixture: ComponentFixture<PlacementComponent>;
        let service: PlacementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [PlacementComponent],
                providers: [
                    PlacementService
                ]
            })
            .overrideTemplate(PlacementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlacementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlacementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Placement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.placements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
