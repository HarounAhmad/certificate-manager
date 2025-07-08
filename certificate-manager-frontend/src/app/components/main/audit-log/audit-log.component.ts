import {Component, OnInit, ViewChild} from '@angular/core';
import {AuditLogService} from "../../../service/audit-log.service";
import {Table, TableLazyLoadEvent, TableModule} from "primeng/table";
import {InputText} from "primeng/inputtext";
import {IconField} from "primeng/iconfield";
import {InputIcon} from "primeng/inputicon";
import {FormsModule} from "@angular/forms";
import {Calendar} from "primeng/calendar";
import {DatePicker} from "primeng/datepicker";
import {MultiSelect} from "primeng/multiselect";

@Component({
  selector: 'app-audit-log',
  imports: [
    TableModule,
    InputText,
    IconField,
    InputIcon,
    FormsModule,
    Calendar,
    DatePicker,
    MultiSelect
  ],
  templateUrl: './audit-log.component.html',
  standalone: true,
  styleUrl: './audit-log.component.scss'
})
export class AuditLogComponent implements OnInit{

  logs: any[] = [];
  idFilter = '';
  actionFilter = '';
  actorFilter = '';
  timestampFilter: Date[] = [];
  affectedUserFilter = '';
  actionTypeFilter = '';


  @ViewChild('dt') dt!: Table;
  loading: boolean = false;
  totalRecords: number = 0;


  actionTypeOptions: { label: string, value: string }[] = []
  selectedActionTypes: any;

  constructor(
    private auditLogService: AuditLogService,
  ) {}

  ngOnInit() {
/*    this.auditLogService.getAuditLogs(0, 10).subscribe(
      response => {
        this.logs = response.content || [];
        console.log('Audit logs fetched successfully:', this.logs)
      },
      error => {
        console.error('Error fetching audit logs:', error);
      }
    );*/
    this.auditLogService.getActionTypes().subscribe(
      response => {
        this.actionTypeOptions = response.map((type: string) => ({ label: type, value: type }));
      },
      error => {
        console.error('Error fetching action types:', error);
      }
    );
  }

  onGlobalFilter(event: Event) {
    const input = event.target as HTMLInputElement;
    this.dt.filterGlobal(input.value, 'contains');
  }

  loadLogsLazy(event: TableLazyLoadEvent) {
    this.loading = true;
    this.auditLogService.lazyLoadAuditLogs(event).subscribe(response => {
      this.logs = response.content;
      this.totalRecords = response.totalElements;
      this.loading = false;
    });
  }
}
